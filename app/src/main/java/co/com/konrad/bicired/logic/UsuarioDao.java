package co.com.konrad.bicired.logic;

public class UsuarioDao {

    private String correo;
    private String genero;
    private String nombre;
    private String clave;

    public UsuarioDao(String correo, String genero, String nombre) {
        this.correo = correo;
        this.genero = genero;
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "correo='" + correo + '\'' +
                ", genero='" + genero + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
