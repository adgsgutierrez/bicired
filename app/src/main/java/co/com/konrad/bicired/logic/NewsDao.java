package co.com.konrad.bicired.logic;

import java.util.ArrayList;
import java.util.Date;

public class NewsDao {

    private ArrayList<NewDao> newDao;

    public void initNewDao(){
        this.newDao = new ArrayList();
        for(int i = 0 ; i < 2 ; i++){
            NewDao newObject = new NewDao();
            newObject.setUsuario(new UsuarioDao("prueba@prueba.com", "M" , "Prueba" ));
            newObject.setDescripcion("Ruta de Prueba "+(i+1));
            newObject.setFechaEvento(new Date());
            newObject.setRuta("https://www.google.com/maps/dir/4.6503152,-74.0614899/4.6590839,-74.0621336/@4.6513632,-74.0681418,15z/data=!4m2!4m1!3e3");
            this.newDao.add(newObject);
        }
    }

    public ArrayList<NewDao> getNewDao() {
        return newDao;
    }

    public void setNewDao(ArrayList<NewDao> newDao) {
        this.newDao = newDao;
    }

    @Override
    public String toString() {
        return "NewsDao{" +
                "newDao=" + newDao +
                '}';
    }
}
