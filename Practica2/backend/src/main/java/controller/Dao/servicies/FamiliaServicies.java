package controller.Dao.servicies;
import controller.tda.list.LinkedList;
import models.Familia;
import controller.Dao.FamiliaDao;

public class FamiliaServicies {
    private FamiliaDao obj;
    public FamiliaServicies(){
        obj = new FamiliaDao();
    }
    public Boolean save() throws Exception{
        return obj.save();
    }
    public Boolean update() throws Exception{
        return obj.update();
    }
    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id); 
    }
    
    public LinkedList listAll(){
        return obj.getlistAll();
    }

    public Familia getFamilia(){
        return obj.getFamilia();
    }

    public void setFamilia( Familia Familia){
        obj.setFamilia(Familia);
    }

    public Familia get(Integer id) throws Exception {
        return obj.get(id);
    }

  
}