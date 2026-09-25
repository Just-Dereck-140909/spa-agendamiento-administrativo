package main.java.org.wellness.spa.agendamiento.administrativo.service.cliente;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.cliente.ClienteRepository;

import java.text.Normalizer;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public void guardarCliente(Cliente cliente) {

        String nuevoId = generarSiguienteIdCliente();
        cliente.setIdCliente(nuevoId);

        String correoGenerado = generarCorreo(
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                nuevoId
        );

        cliente.setClienteCorreoElectronico(correoGenerado);

        clienteRepository.save(cliente);
    }

    public void actualizarCliente(Cliente cliente) {
        clienteRepository.update(cliente);
    }

    public void eliminarCliente(String id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listar();
    }

    public Cliente buscarClientePorId(String id) {
        return clienteRepository.buscarPorId(id);
    }
    
    public boolean tieneCitasAsociadas(String idCliente) {
        return clienteRepository.tieneCitasAsociadas(idCliente);
    }

    private String generarSiguienteIdCliente() {

        String ultimoId = clienteRepository.obtenerUltimoId();

        int numero = 1;

        if (ultimoId != null && !ultimoId.isBlank()) {

            String parteNumerica =
                    ultimoId.replaceAll("[^0-9]", "");

            if (!parteNumerica.isEmpty()) {
                numero = Integer.parseInt(parteNumerica) + 1;
            }
        }

        return String.format("CL%03d", numero);
    }

    private String generarCorreo(
            String nombre,
            String apellido,
            String idCliente) {

        String nombreLimpio = normalizarTexto(nombre);
        String apellidoLimpio = normalizarTexto(apellido);

        String ultimosTresDigitos =
                idCliente.substring(idCliente.length() - 3);

        return nombreLimpio
                + "."
                + apellidoLimpio
                + ultimosTresDigitos
                + "@wellness.spa.com";
    }

    private String normalizarTexto(String texto) {

        String textoNormalizado = Normalizer.normalize(
                texto.trim().toLowerCase(),
                Normalizer.Form.NFD
        );

        return textoNormalizado
                .replaceAll("\\p{M}", "");
    }
}