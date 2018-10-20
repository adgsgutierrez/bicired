package co.com.konrad.bicired.logic;

public class Fotografia {

    private String fecha;
    private String imagenes;
    private String usuario;


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "{" +
                "fecha='" + fecha + '\'' +
                ", imagenes='" + imagenes + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
