package co.com.konrad.bicired.logic;

public class ResponseDao {

    private Integer codigo;
    private String mensaje;
    private Object datos;

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

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "ResponseDao{" +
                "codigo=" + codigo +
                ", mensaje='" + mensaje + '\'' +
                ", datos='" + datos + '\'' +
                '}';
    }
}
