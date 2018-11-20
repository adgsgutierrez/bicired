package co.com.konrad.bicired.logic;

import java.util.ArrayList;
import java.util.Date;

public class NewDao {

    private String pk_pbl_id;
    private String pbl_fecha;
//    private String pbl_ltd_origen;
//    private String pbl_ltg_origen;
//    private String pbl_ltd_destino;
//    private String pbl_ltg_destino;
    private ArrayList<Point> pbl_ruta;

    private String pbl_descripcion;
    private String fk_pbl_usr_correo;
    private String number_fecha;


    public String getPk_pbl_id() {
        return pk_pbl_id;
    }

    public void setPk_pbl_id(String pk_pbl_id) {
        this.pk_pbl_id = pk_pbl_id;
    }

    public String getPbl_fecha() {
        return pbl_fecha;
    }

    public void setPbl_fecha(String pbl_fecha) {
        this.pbl_fecha = pbl_fecha;
    }

    public ArrayList<Point> getPbl_ruta() {
        return pbl_ruta;
    }

    public void setPbl_ruta(ArrayList<Point> pbl_ruta) {
        this.pbl_ruta = pbl_ruta;
    }

    public String getPbl_descripcion() {
        return pbl_descripcion;
    }

    public void setPbl_descripcion(String pbl_descripcion) {
        this.pbl_descripcion = pbl_descripcion;
    }

    public String getFk_pbl_usr_correo() {
        return fk_pbl_usr_correo;
    }

    public void setFk_pbl_usr_correo(String fk_pbl_usr_correo) {
        this.fk_pbl_usr_correo = fk_pbl_usr_correo;
    }

    public String getNumber_fecha() {
        return number_fecha;
    }

    public void setNumber_fecha(String number_fecha) {
        this.number_fecha = number_fecha;
    }

    @Override
    public String toString() {
        return "NewDao{" +
                "pk_pbl_id='" + pk_pbl_id + '\'' +
                ", pbl_fecha='" + pbl_fecha + '\'' +
                ", pbl_ruta=" + pbl_ruta +
                ", pbl_descripcion='" + pbl_descripcion + '\'' +
                ", fk_pbl_usr_correo='" + fk_pbl_usr_correo + '\'' +
                ", number_fecha='" + number_fecha + '\'' +
                '}';
    }
}
