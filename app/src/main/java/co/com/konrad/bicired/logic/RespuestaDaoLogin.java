package co.com.konrad.bicired.logic;

public class RespuestaDaoLogin {

    private Integer codigo;
    private String mensaje;
    private UsuarioDao datos;

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

    public UsuarioDao getDatos() {
        return datos;
    }

    public void setDatos(UsuarioDao datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "RespuestaDaoLogin{" +
                "codigo=" + codigo +
                ", mensaje='" + mensaje + '\'' +
                ", datos=" + datos +
                '}';
    }
}
