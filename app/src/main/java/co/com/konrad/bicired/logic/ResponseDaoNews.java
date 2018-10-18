package co.com.konrad.bicired.logic;

import java.util.ArrayList;

public class ResponseDaoNews {

    private Integer codigo;
    private String mensaje;
    private ArrayList<NewDao> datos;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<NewDao> getNewDao() {
        return datos;
    }

    public void setNewDao(ArrayList<NewDao> newDao) {
        this.datos = newDao;
    }

    @Override
    public String toString() {
        return "ResponseDaoNews{" +
                "codigo=" + codigo +
                ", mensaje='" + mensaje + '\'' +
                ", datos=" + datos +
                '}';
    }
}
