package controller.Dao;

import models.Generador;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class GeneradorDao extends AdapterDao<Generador> {
    private Generador generador; 
    private LinkedList<Generador> listAll;
    
    public GeneradorDao(){
        super(Generador.class);
        this.listAll = new LinkedList<>();
    }

    public Generador getGenerador() {
        if (generador == null) {
            generador = new Generador(); 
        }
        return this.generador;
    }

    public void setGenerador(Generador generador){
        this.generador = generador;
    }

    public LinkedList<Generador> getlistAll(){
        if (listAll.isEmpty()) { 
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        generador.setIdGenerador(id);
        this.persist(this.generador);
        this.listAll = getlistAll(); 
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getGenerador(), getGenerador().getIdGenerador() - 1);
            this.listAll = getlistAll(); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Generador> list = getlistAll(); 
        Generador generador = get(id); 
        if (generador != null) {
            list.remove(generador); 
            String info = g.toJson(list.toArray());
            saveFile(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Generador con id " + id + " no encontrado.");
            return false;
        }
    }

}