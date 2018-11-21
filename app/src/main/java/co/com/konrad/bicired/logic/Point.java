package co.com.konrad.bicired.logic;

public class Point {

    private String latitud;
    private String longitud;

    @Override
    public String toString() {
        return "{" +
                "\"latitud\":" + latitud +
                ",\"longitud\":" + longitud +
                '}';
    }

    public String getLatitud() {
        if(latitud==null){
            return "0";
        }
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {

        if(longitud==null){
            return "0";
        }
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
