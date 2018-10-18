package co.com.konrad.bicired.logic;

public class UsuarioDao {

    private String nombre;
    private String correo;
    private String genero;
    private String foto;

    public UsuarioDao(String correo, String genero, String nombre , String foto) {
        this.correo = correo;
        this.genero = genero;
        this.nombre = nombre;
        this.foto = foto;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String clave) {
        this.foto = clave;
    }

    @Override
    public String toString() {
        return "UsuarioDao{" +
                "correo='" + correo + '\'' +
                ", genero='" + genero + '\'' +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
