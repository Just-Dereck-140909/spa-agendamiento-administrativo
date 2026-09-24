package main.java.org.wellness.spa.agendamiento.administrativo.service.cliente;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.clientes.ClienteRepository;
import java.util.List;

public class ClienteService {
 
    private final ClienteRepository clienteRepository;
 
    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }
 
    public void guardarCliente(Cliente cliente) {
        String nuevoId = generarSiguienteIdCliente();
        cliente.setIdCliente(nuevoId);
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
 
    private String generarSiguienteIdCliente() {
        String ultimoId = clienteRepository.obtenerUltimoId();
        int numero = 1;
 
        if (ultimoId != null && !ultimoId.isBlank()) {
            String parteNumerica = ultimoId.replaceAll("[^0-9]", "");
            if (!parteNumerica.isEmpty()) {
                numero = Integer.parseInt(parteNumerica) + 1;
            }
        }
 
        return String.format("CL%03d", numero);
    }
}