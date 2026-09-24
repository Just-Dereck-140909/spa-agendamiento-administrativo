package main.java.org.wellness.spa.agendamiento.administrativo.controller.cliente;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cliente.ClienteService;

public class ClienteFormController {

    @FXML
    private Label lblTituloFormulario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCerrar;

    private final ClienteService clienteService = new ClienteService();

    private Cliente clienteActual;
    private ClientesController controllerPadre;

    @FXML
    public void initialize() {

        txtTelefono.setTextFormatter(
                new javafx.scene.control.TextFormatter<String>(change -> {

                    String nuevoTexto = change.getControlNewText();

                    if (nuevoTexto.matches("\\d{0,8}")) {
                        return change;
                    }

                    return null;
                })
        );
    }


    public void inicializar(Cliente cliente, ClientesController controllerPadre) {

        this.controllerPadre = controllerPadre;
        this.clienteActual = cliente;

        if (cliente != null) {

            lblTituloFormulario.setText("Actualizar Cliente");

            txtNombre.setText(cliente.getNombreCliente());
            txtApellido.setText(cliente.getApellidoCliente());
            txtTelefono.setText(cliente.getTelefonoCliente());

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

            nuevoCliente.setNombreCliente(
                    txtNombre.getText().trim()
            );

            nuevoCliente.setApellidoCliente(
                    txtApellido.getText().trim()
            );

            nuevoCliente.setTelefonoCliente(
                    txtTelefono.getText().trim()
            );

            clienteService.guardarCliente(nuevoCliente);

            mostrarAlertaCreacion(nuevoCliente);

        } else {

            clienteActual.setNombreCliente(
                    txtNombre.getText().trim()
            );

            clienteActual.setApellidoCliente(
                    txtApellido.getText().trim()
            );

            clienteActual.setTelefonoCliente(
                    txtTelefono.getText().trim()
            );

            clienteService.actualizarCliente(clienteActual);

            mostrarAlertaActualizacion();
        }

        if (controllerPadre != null) {
            controllerPadre.cargarClientes();
        }

        cerrarVentana();
    }

    private void mostrarAlertaCreacion(Cliente cliente) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Wellness Spa");
        alerta.setHeaderText("Cliente registrado correctamente");

        alerta.setContentText(
                "ID del cliente: " + cliente.getIdCliente()
                + "\nCorreo generado: " + cliente.getClienteCorreoElectronico()
        );

        alerta.showAndWait();
    }

    private void mostrarAlertaActualizacion() {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Wellness Spa");
        alerta.setHeaderText("Cliente actualizado correctamente");
        alerta.setContentText("Los datos del cliente fueron actualizados.");

        alerta.showAndWait();
    }

    @FXML
    private void handleCerrarAction(ActionEvent event) {

        boolean hayDatos
                = !txtNombre.getText().trim().isEmpty()
                || !txtApellido.getText().trim().isEmpty()
                || !txtTelefono.getText().trim().isEmpty();

        if (hayDatos) {

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

            alerta.setTitle("Cerrar formulario");
            alerta.setHeaderText("Hay datos ingresados");

            alerta.setContentText(
                    "¿Está seguro de que desea cerrar? "
                    + "Se perderán los datos ingresados."
            );

            ButtonType botonSi = new ButtonType("Sí");
            ButtonType botonNo = new ButtonType("No");

            alerta.getButtonTypes().setAll(botonSi, botonNo);

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent()
                    && resultado.get() == botonSi) {

                cerrarVentana();
            }

        } else {

            cerrarVentana();
        }
    }

    private boolean validarCampos() {

        if (txtNombre.getText().isBlank()
                || txtApellido.getText().isBlank()
                || txtTelefono.getText().isBlank()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);

            alerta.setTitle("Wellness Spa");
            alerta.setHeaderText("Campos incompletos");
            alerta.setContentText(
                    "Todos los campos son obligatorios."
            );

            alerta.showAndWait();

            return false;
        }

        String telefono = txtTelefono.getText().trim();

        if (!telefono.matches("\\d+")) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);

            alerta.setTitle("Wellness Spa");
            alerta.setHeaderText("Teléfono inválido");
            alerta.setContentText(
                    "El teléfono solo puede contener números."
            );

            alerta.showAndWait();

            return false;
        }

        if (telefono.length() > 8) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);

            alerta.setTitle("Wellness Spa");
            alerta.setHeaderText("Teléfono inválido");
            alerta.setContentText(
                    "El teléfono no puede tener más de 8 dígitos."
            );

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