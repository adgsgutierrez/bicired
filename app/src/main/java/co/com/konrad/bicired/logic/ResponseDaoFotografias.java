package co.com.konrad.bicired.logic;

import java.util.ArrayList;

public class ResponseDaoFotografias {

    private Integer codigo;
    private String mensaje;
    private ArrayList<Fotografia> datos;

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

    public ArrayList<Fotografia> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Fotografia> datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "ResponseDaoFotografias{" +
                "codigo=" + codigo +
                ", mensaje='" + mensaje + '\'' +
                ", datos=" + datos +
                '}';
    }
}
