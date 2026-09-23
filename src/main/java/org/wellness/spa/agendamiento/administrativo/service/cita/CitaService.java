package main.java.org.wellness.spa.agendamiento.administrativo.service.cita;

import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.Cita;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaResumen;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.cita.CitaRepository;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaDetalle;
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService() {
        citaRepository = new CitaRepository();
    }
    
    public List<CitaDetalle> listarCitasDetalle() {
        return citaRepository.listarCitasDetalle();
    }
    
    //Guardar cita 
    public void guardarCita(Cita cita) {

        String nuevoId = generarSiguienteIdCita();

        cita.setIdCita(nuevoId);
        cita.setEstadoCita("Pendiente");

        citaRepository.save(cita);
    }
    
    
    //Actualizar cita
    public void actualizarCita(Cita cita) {
        citaRepository.update(cita);
    }
    
    
    //Eliminar cita
    public void eliminarCita(String id) {
        citaRepository.deleteById(id);
    }
    
    //listar datos de citas
    public List<Cita> listarCitas() {
        return citaRepository.listar();
    }
    
    //Buscar una cita por ID
    public Cita buscarCitaPorId(String id) {
        return citaRepository.buscarPorId(id);
    }
    
    
    //Cargar la Tabla del Menu Principal
    public List<CitaResumen> listarCitasResumen() {
        return citaRepository.listarCitasResumen();
    }
    
    //Metodo para general el siguiente ID al guardar.
    public String generarSiguienteIdCita() {

        String ultimoId = citaRepository.obtenerUltimoId();

        if (ultimoId == null) {return "CI001";}

        int numero = Integer.parseInt(ultimoId.substring(2));
        numero++;

        return String.format("CI%03d", numero);
    }
    
}