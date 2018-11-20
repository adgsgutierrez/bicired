package co.com.konrad.bicired.logic;

public class Point {

    private String latitud;
    private String longitud;

    @Override
    public String toString() {
        return "{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
