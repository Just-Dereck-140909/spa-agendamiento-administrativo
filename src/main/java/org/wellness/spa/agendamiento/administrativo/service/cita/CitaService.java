package main.java.org.wellness.spa.agendamiento.administrativo.service.cita;

import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaResumen;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.cita.CitaRepository;

public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService() {
        citaRepository = new CitaRepository();
    }

    public List<CitaResumen> listarCitasResumen() {
        return citaRepository.listarCitasResumen();
    }
}