package co.com.konrad.bicired.logic;

import java.util.ArrayList;

public class ResponseDaoFoto {
    private Integer codigo;
    private String mensaje;
    private String datos;

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

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "{" +
                "codigo=" + codigo +
                ", mensaje='" + mensaje + '\'' +
                ", datos='" + datos + '\'' +
                '}';
    }
}
