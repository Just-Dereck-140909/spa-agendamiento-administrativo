package main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador;


public class TrabajadorDetalle {
    
    private String idTrabajador;
    private String nombreTrabajador;
    private String apellidoTrabajador;
    private String ocupacion;
    private String trabajadorCorreoElectronico;
    
    public TrabajadorDetalle(){
    }

    public TrabajadorDetalle(String idTrabajador, String nombreTrabajador, String apellidoTrabajador, String ocupacion, String trabajadorCorreoElectronico) {
        this.idTrabajador = idTrabajador;
        this.nombreTrabajador = nombreTrabajador;
        this.apellidoTrabajador = apellidoTrabajador;
        this.ocupacion = ocupacion;
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getTrabajadorCorreoElectronico() {
        return trabajadorCorreoElectronico;
    }

    public void setTrabajadorCorreoElectronico(String trabajadorCorreoElectronico) {
        this.trabajadorCorreoElectronico = trabajadorCorreoElectronico;
    }
    
    
}
