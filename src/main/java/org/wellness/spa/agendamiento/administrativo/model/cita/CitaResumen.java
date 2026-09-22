
package main.java.org.wellness.spa.agendamiento.administrativo.model.cita;

import java.time.LocalDate;
import java.time.LocalTime;

//Modelo para llenar los datos de la TableView del Menu Principal

public class CitaResumen {
    private String nombreCliente;
    private String apellidoCliente;
    private String trabajador;
    private String tratamiento;
    private String descripcionTratamiento;
    private LocalTime hora;
    private LocalDate fecha;
    private String estado;
    
    public CitaResumen(){
    }

    public CitaResumen(String nombreCliente, String apellidoCliente, String trabajador, String tratamiento, String descripcionTratamiento, LocalTime hora, LocalDate fecha, String estado) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.trabajador = trabajador;
        this.tratamiento = tratamiento;
        this.descripcionTratamiento = descripcionTratamiento;
        this.hora = hora;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDescripcionTratamiento() {
        return descripcionTratamiento;
    }

    public void setDescripcionTratamiento(String descripcionTratamiento) {
        this.descripcionTratamiento = descripcionTratamiento;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
