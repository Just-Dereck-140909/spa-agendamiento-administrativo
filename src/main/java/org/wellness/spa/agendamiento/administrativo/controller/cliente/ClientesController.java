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

@FXML
private TableView<Cliente> tablaClientes;

@FXML
private TableColumn<Cliente, String> colAtributo1; 

@FXML
private TableColumn<Cliente, String> colAtributo2; 

@FXML
private TableColumn<Cliente, String> colAtributo3; 

@FXML
private TableColumn<Cliente, String> colAtributo4; 

@FXML
private TableColumn<Cliente, String> colAtributo5; 

@FXML
private Button btnNuevoCliente;

@FXML
private Button btnActualizarCliente;

@FXML
private Button btnEliminarCliente;

@FXML
private Button btnVolverMenu;

    private final ClienteService clienteService = new ClienteService();

    private final ObservableList<Cliente> listaClientes
            = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        configurarColumnas();
        cargarClientes();
    }

    private void configurarColumnas() {

        colAtributo1.setCellValueFactory(
                new PropertyValueFactory<>("idCliente")
        );

        colAtributo2.setCellValueFactory(
                new PropertyValueFactory<>("nombreCliente")
        );

        colAtributo3.setCellValueFactory(
                new PropertyValueFactory<>("apellidoCliente")
        );

        colAtributo4.setCellValueFactory(
                new PropertyValueFactory<>("telefonoCliente")
        );

        colAtributo5.setCellValueFactory(
                new PropertyValueFactory<>("clienteCorreoElectronico")
        );
    }

    public void cargarClientes() {

        listaClientes.setAll(
                clienteService.listarClientes()
        );

        tablaClientes.setItems(listaClientes);
    }

    @FXML
    private void handleNuevoClienteAction(ActionEvent event) {

        abrirFormulario(null, event);
    }

    @FXML
    private void handleActualizarClienteAction(ActionEvent event) {

        Cliente clienteSeleccionado
                = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Selecciona un cliente",
                    "Selecciona un cliente para actualizar."
            );

            return;
        }

        abrirFormulario(clienteSeleccionado, event);
    }

    @FXML
    private void handleEliminarClienteAction(ActionEvent event) {

        Cliente clienteSeleccionado
                = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Selecciona un cliente",
                    "Selecciona un cliente para eliminar."
            );

            return;
        }

        Alert confirmacion
                = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Wellness Spa");
        confirmacion.setHeaderText("Eliminar cliente");

        confirmacion.setContentText(
                "¿Está seguro de que desea eliminar a "
                + clienteSeleccionado.getNombreCliente()
                + " "
                + clienteSeleccionado.getApellidoCliente()
                + "?"
        );

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");

        confirmacion.getButtonTypes().setAll(
                botonSi,
                botonNo
        );

        Optional<ButtonType> resultado
                = confirmacion.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == botonSi) {

            clienteService.eliminarCliente(
                    clienteSeleccionado.getIdCliente()
            );

            cargarClientes();

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Cliente eliminado",
                    "El cliente fue eliminado correctamente."
            );
        }
    }

    @FXML
    private void handleVolverMenuAction(ActionEvent event) {

        try {

            Stage stageActual = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            SceneManager sceneManager
                    = new SceneManager(stageActual);

            sceneManager.cambiarEscena(
                    "/main/resources/view/menu-principal.fxml"
            );

        } catch (IOException e) {

            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No fue posible regresar al menú principal."
            );
        }
    }

    private void abrirFormulario(
            Cliente clienteSeleccionado,
            ActionEvent event) {

        try {

            Stage stageActual = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            SceneManager sceneManager
                    = new SceneManager(stageActual);

            String titulo;

            if (clienteSeleccionado == null) {
                titulo = "Nuevo Cliente";
            } else {
                titulo = "Actualizar Cliente";
            }

            FXMLLoader loader
                    = sceneManager.abrirVentanaConControlador(
                            "/main/resources/view/cliente/cliente-form.fxml",
                            titulo
                    );

            ClienteFormController controller
                    = loader.getController();

            controller.inicializar(
                    clienteSeleccionado,
                    this
            );

        } catch (IOException e) {

            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No fue posible abrir el formulario de clientes."
            );
        }
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String headerText,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle("Wellness Spa");
        alerta.setHeaderText(headerText);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

}
