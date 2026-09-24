package main.java.org.wellness.spa.agendamiento.administrativo.service.trabajador;

import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.Trabajador;
import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.TrabajadorDetalle;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.trabajador.TrabajadorRepository;

public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorService() {
        trabajadorRepository = new TrabajadorRepository();
    }

    
    public void guardarTrabajador(Trabajador trabajador) {

        String idGenerado = generarSiguienteId();

        trabajador.setIdTrabajador(idGenerado);

        String correoGenerado = generarCorreo(
                trabajador.getNombreTrabajador(),
                trabajador.getApellidoTrabajador(),
                idGenerado
        );

        trabajador.setTrabajadorCorreoElectronico(correoGenerado);

        trabajadorRepository.save(trabajador);
    }

    public void actualizarTrabajador(Trabajador trabajador) {
        trabajadorRepository.update(trabajador);
    }

    public void eliminarTrabajador(String id) {
        trabajadorRepository.deleteById(id);
    }

    public List<Trabajador> listarTrabajadores() {
        return trabajadorRepository.listar();
    }

    public Trabajador buscarTrabajadorPorId(String id) {
        return trabajadorRepository.buscarPorId(id);
    }

    public List<TrabajadorDetalle> listarTrabajadoresDetalle() {
        return trabajadorRepository.listarTrabajadoresDetalle();
    }

    private String generarSiguienteId() {

        String ultimoId = trabajadorRepository.obtenerUltimoId();

        if (ultimoId == null) {
            return "TR001";
        }

        int numero = Integer.parseInt(
                ultimoId.substring(2)
        );

        numero++;

        return String.format("TR%03d", numero);
    }

    private String generarCorreo(String nombre, String apellido, String idTrabajador) {

        String nombreLimpio = nombre.trim().toLowerCase();
        String apellidoLimpio = apellido.trim().toLowerCase();

        String ultimosTresDigitos =
                idTrabajador.substring(idTrabajador.length() - 3);

        return nombreLimpio
                + "."
                + apellidoLimpio
                + ultimosTresDigitos
                + "@wellness.spa.com";
    }
}