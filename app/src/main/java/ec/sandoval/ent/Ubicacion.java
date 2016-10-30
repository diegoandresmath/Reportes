package ec.sandoval.ent;

import java.util.Date;

/**
 * Created by ddelacruz on 21/10/2016.
 */

public class Ubicacion {
    private int id;
    private String fecha;
    private Float latitud;
    private Float longitud;

    public Ubicacion() {
    }

    public Ubicacion(int id, String fecha, Float latitud, Float longitud) {
        this.setId(id);
        this.setFecha(fecha);
        this.setLatitud(latitud);
        this.setLongitud(longitud);
    }

    public Ubicacion(String fecha, Float latitud, Float longitud) {
        this.setFecha(fecha);
        this.setLatitud(latitud);
        this.setLongitud(longitud);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
}
