package main.java.org.wellness.spa.agendamiento.administrativo.service.cita;

import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.OpcionComboBox;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.cita.CitaFormRepository;

public class CitaFormService {

    private final CitaFormRepository citaFormRepository;

    public CitaFormService() {
        citaFormRepository = new CitaFormRepository();
    }

    public List<OpcionComboBox> listarClientes() {
        return citaFormRepository.listarClientes();
    }

    public List<OpcionComboBox> listarTrabajadores() {
        return citaFormRepository.listarTrabajadores();
    }

    public List<OpcionComboBox> listarTratamientos() {
        return citaFormRepository.listarTratamientos();
    }
}