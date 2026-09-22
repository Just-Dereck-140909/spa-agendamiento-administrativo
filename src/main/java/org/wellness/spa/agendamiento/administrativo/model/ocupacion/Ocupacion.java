package main.java.org.wellness.spa.agendamiento.administrativo.model.ocupacion;

public class Ocupacion {

    private String idOcupacion;
    private String nombreOcupacion;
    private String descripcionOcupacion;

    public Ocupacion() {
    }

    public Ocupacion(String idOcupacion, String nombreOcupacion,
            String descripcionOcupacion) {

        this.idOcupacion = idOcupacion;
        this.nombreOcupacion = nombreOcupacion;
        this.descripcionOcupacion = descripcionOcupacion;
    }

    public String getIdOcupacion() {
        return idOcupacion;
    }

    public void setIdOcupacion(String idOcupacion) {
        this.idOcupacion = idOcupacion;
    }

    public String getNombreOcupacion() {
        return nombreOcupacion;
    }

    public void setNombreOcupacion(String nombreOcupacion) {
        this.nombreOcupacion = nombreOcupacion;
    }

    public String getDescripcionOcupacion() {
        return descripcionOcupacion;
    }

    public void setDescripcionOcupacion(String descripcionOcupacion) {
        this.descripcionOcupacion = descripcionOcupacion;
    }
}