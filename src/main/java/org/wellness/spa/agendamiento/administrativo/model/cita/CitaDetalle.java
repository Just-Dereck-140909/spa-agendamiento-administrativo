package main.java.org.wellness.spa.agendamiento.administrativo.model.cita;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDetalle {

    private String idCita;
    private String cliente;
    private String trabajador;
    private String tratamiento;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;

    public CitaDetalle() {
    }

    public CitaDetalle(String idCita, String cliente, String trabajador,
            String tratamiento, LocalDate fecha, LocalTime hora,
            String estado) {

        this.idCita = idCita;
        this.cliente = cliente;
        this.trabajador = trabajador;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}