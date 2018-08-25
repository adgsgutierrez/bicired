package co.com.konrad.bicired.logic;

import java.util.Date;

public class NewDao {

    private String ruta;
    private Date fechaEvento;
    private UsuarioDao usuario;
    private String descripcion;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Date getFechaEvento() {
        if(fechaEvento == null){
            return new Date();
        }
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public UsuarioDao getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDao usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "New{" +
                "ruta='" + ruta + '\'' +
                ", fechaEvento=" + fechaEvento +
                ", usuario=" + usuario +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
