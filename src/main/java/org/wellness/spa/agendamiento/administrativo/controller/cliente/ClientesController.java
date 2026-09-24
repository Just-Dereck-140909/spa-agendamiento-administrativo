package main.java.org.wellness.spa.agendamiento.administrativo.controller.cliente;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
 
import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cliente.ClienteService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;
 
public class ClientesController {
 
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colAtributo1; // ID
    @FXML private TableColumn<Cliente, String> colAtributo2; // Nombre
    @FXML private TableColumn<Cliente, String> colAtributo3; // Apellido
    @FXML private TableColumn<Cliente, String> colAtributo4; // Teléfono
    @FXML private TableColumn<Cliente, String> colAtributo5; // Correo
 
    @FXML private Button btnNuevoCliente;
    @FXML private Button btnActualizarCliente;
    @FXML private Button btnEliminarCliente;
    @FXML private Button btnVolverMenu;
 
    private final ClienteService clienteService = new ClienteService();
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
 
    @FXML
    public void initialize() {
        configurarColumnas();
        cargarClientes();
    }
 
    private void configurarColumnas() {
        colAtributo1.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colAtributo2.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colAtributo3.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
        colAtributo4.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colAtributo5.setCellValueFactory(new PropertyValueFactory<>("clienteCorreoElectronico"));
    }
 
    public void cargarClientes() {
        listaClientes.setAll(clienteService.listarClientes());
        tablaClientes.setItems(listaClientes);
    }
 
    @FXML
    private void handleNuevoClienteAction(ActionEvent event) {
        abrirFormulario(null, event);
    }
 
    @FXML
    private void handleActualizarClienteAction(ActionEvent event) {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
 
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un cliente", "Selecciona un cliente para actualizar.");
            return;
        }
 
        abrirFormulario(seleccionado, event);
    }
 
    @FXML
    private void handleEliminarClienteAction(ActionEvent event) {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
 
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un cliente", "Selecciona un cliente para eliminar.");
            return;
        }
 
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Wellness Spa");
        confirmacion.setHeaderText("Eliminar cliente");
        confirmacion.setContentText("¿Seguro que deseas eliminar a "
                + seleccionado.getNombreCliente() + " " + seleccionado.getApellidoCliente() + "?");
 
        Optional<ButtonType> resultado = confirmacion.showAndWait();
 
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            clienteService.eliminarCliente(seleccionado.getIdCliente());
            cargarClientes();
        }
    }
 
    @FXML
    private void handleVolverMenuAction(ActionEvent event) {
        try {
            SceneManager sceneManager = new SceneManager(
                    (Stage) ((Node) event.getSource()).getScene().getWindow()
            );
            sceneManager.cambiarEscena("/main/resources/view/menu-principal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private void abrirFormulario(Cliente clienteSeleccionado, ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneManager sceneManager = new SceneManager(stageActual);
 
            String titulo = clienteSeleccionado == null ? "Nuevo Cliente" : "Actualizar Cliente";
 
            FXMLLoader loader = sceneManager.abrirVentanaConControlador(
                    "/main/resources/view/cliente/cliente-form.fxml", titulo
            );
 
            ClienteFormController controller = loader.getController();
            controller.inicializar(clienteSeleccionado, this);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private void mostrarAlerta(Alert.AlertType tipo, String headerText, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Wellness Spa");
        alerta.setHeaderText(headerText);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}