package main.java.org.wellness.spa.agendamiento.administrativo.service.tratamiento;

import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.model.tratamiento.Tratamiento;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.tratamiento.TratamientoRepository;

public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;

    public TratamientoService() {
        tratamientoRepository = new TratamientoRepository();
    }

    public void guardar(Tratamiento tratamiento) {

        String ultimoId = tratamientoRepository.obtenerUltimoId();

        int siguienteNumero = 1;

        if (ultimoId != null && ultimoId.startsWith("TA")) {
            int numeroActual = Integer.parseInt(ultimoId.substring(2));
            siguienteNumero = numeroActual + 1;
        }

        String nuevoId = String.format("TA%03d", siguienteNumero);

        tratamiento.setIdTratamiento(nuevoId);

        tratamientoRepository.save(tratamiento);
    }

    public void actualizar(Tratamiento tratamiento) {
        tratamientoRepository.update(tratamiento);
    }

    public void eliminar(String id) {
        tratamientoRepository.deleteById(id);
    }

    public List<Tratamiento> listar() {
        return tratamientoRepository.listar();
    }

    public Tratamiento buscarPorId(String id) {
        return tratamientoRepository.buscarPorId(id);
    }
    
    public boolean tieneCitasAsociadas(String idTratamiento) {
        return tratamientoRepository.tieneCitasAsociadas(idTratamiento);
    }
    
}