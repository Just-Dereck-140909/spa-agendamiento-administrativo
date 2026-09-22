package main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador;

public class Trabajador {

    private String idTrabajador;
    private String nombreTrabajador;
    private String apellidoTrabajador;
    private String idOcupacion;
    private String trabajadorCorreoElectronico;

    public Trabajador() {
    }

    public Trabajador(String idTrabajador, String nombreTrabajador,
            String apellidoTrabajador, String idOcupacion,
            String trabajadorCorreoElectronico) {

        this.idTrabajador = idTrabajador;
        this.nombreTrabajador = nombreTrabajador;
        this.apellidoTrabajador = apellidoTrabajador;
        this.idOcupacion = idOcupacion;
        this.trabajadorCorreoElectronico = trabajadorCorreoElectronico;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getApellidoTrabajador() {
        return apellidoTrabajador;
    }

    public void setApellidoTrabajador(String apellidoTrabajador) {
        this.apellidoTrabajador = apellidoTrabajador;
    }

    public String getIdOcupacion() {
        return idOcupacion;
    }

    public void setIdOcupacion(String idOcupacion) {
        this.idOcupacion = idOcupacion;
    }

    public String getTrabajadorCorreoElectronico() {
        return trabajadorCorreoElectronico;
    }

    public void setTrabajadorCorreoElectronico(String trabajadorCorreoElectronico) {
        this.trabajadorCorreoElectronico = trabajadorCorreoElectronico;
    }
}
