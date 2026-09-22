package main.java.org.wellness.spa.agendamiento.administrativo.model.cita;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {

    private String idCita;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estadoCita;
    private String idCliente;
    private String idTrabajador;
    private String idTratamiento;

    public Cita() {
    }

    public Cita(String idCita, LocalDate fechaCita, LocalTime horaCita,
            String estadoCita, String idCliente, String idTrabajador,
            String idTratamiento) {

        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estadoCita = estadoCita;
        this.idCliente = idCliente;
        this.idTrabajador = idTrabajador;
        this.idTratamiento = idTratamiento;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(String idTratamiento) {
        this.idTratamiento = idTratamiento;
    }
}