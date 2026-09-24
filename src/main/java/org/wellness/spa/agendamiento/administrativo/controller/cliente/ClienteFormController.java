package main.java.org.wellness.spa.agendamiento.administrativo.controller.cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cliente.ClienteService;
 
public class ClienteFormController {
 
    @FXML private Label lblTituloFormulario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCerrar;
 
    private final ClienteService clienteService = new ClienteService();
    private Cliente clienteActual;
    private ClientesController controllerPadre;
 
    public void inicializar(Cliente cliente, ClientesController controllerPadre) {
        this.controllerPadre = controllerPadre;
        this.clienteActual = cliente;
 
        if (cliente != null) {
            lblTituloFormulario.setText("Actualizar Cliente");
            txtNombre.setText(cliente.getNombreCliente());
            txtApellido.setText(cliente.getApellidoCliente());
            txtTelefono.setText(cliente.getTelefonoCliente());
            txtCorreo.setText(cliente.getClienteCorreoElectronico());
        } else {
            lblTituloFormulario.setText("Nuevo Cliente");
        }
    }
 
    @FXML
    private void handleGuardarAction() {
        if (!validarCampos()) {
            return;
        }
 
        if (clienteActual == null) {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombreCliente(txtNombre.getText().trim());
            nuevoCliente.setApellidoCliente(txtApellido.getText().trim());
            nuevoCliente.setTelefonoCliente(txtTelefono.getText().trim());
            nuevoCliente.setClienteCorreoElectronico(txtCorreo.getText().trim());
            clienteService.guardarCliente(nuevoCliente);
        } else {
            clienteActual.setNombreCliente(txtNombre.getText().trim());
            clienteActual.setApellidoCliente(txtApellido.getText().trim());
            clienteActual.setTelefonoCliente(txtTelefono.getText().trim());
            clienteActual.setClienteCorreoElectronico(txtCorreo.getText().trim());
            clienteService.actualizarCliente(clienteActual);
        }
 
        if (controllerPadre != null) {
            controllerPadre.cargarClientes();
        }
 
        cerrarVentana();
    }
 
    @FXML
    private void handleCerrarAction() {
        cerrarVentana();
    }
 
    private boolean validarCampos() {
        if (txtNombre.getText().isBlank()
                || txtApellido.getText().isBlank()
                || txtTelefono.getText().isBlank()
                || txtCorreo.getText().isBlank()) {
 
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Wellness Spa");
            alerta.setHeaderText("Campos incompletos");
            alerta.setContentText("Todos los campos son obligatorios.");
            alerta.showAndWait();
            return false;
        }
        return true;
    }
 
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
 