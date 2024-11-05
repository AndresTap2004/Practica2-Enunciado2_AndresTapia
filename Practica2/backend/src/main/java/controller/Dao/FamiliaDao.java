package controller.Dao;

import models.Familia;  
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class FamiliaDao extends AdapterDao<Familia> {  
    private Familia familia; 
    private LinkedList<Familia> listAll;
    
    public FamiliaDao() {  
        super(Familia.class);
        this.listAll = new LinkedList<>();
    }

    public Familia getFamilia() {  
        if (familia == null) {
            familia = new Familia(0, ""); 
        }
        return this.familia;
    }

    public void setFamilia(Familia familia) { 
        this.familia = familia;
    }

    public LinkedList<Familia> getlistAll() { 
        if (listAll.isEmpty()) { 
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        familia.setIdFamilia(id);  
        this.persist(this.familia);
        this.listAll = getlistAll(); 
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getFamilia(), getFamilia().getIdFamilia() - 1);  
            this.listAll = getlistAll(); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Familia> list = getlistAll(); 
        Familia familia = get(id);  
        if (familia != null) {
            list.remove(familia);  
            String info = g.toJson(list.toArray());
            saveFile(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Familia con id " + id + " no encontrada.");
            return false;
        }
    }
}
