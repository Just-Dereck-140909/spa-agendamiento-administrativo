package main.java.org.wellness.spa.agendamiento.administrativo.controller.trabajador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.Trabajador;
import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.TrabajadorDetalle;
import main.java.org.wellness.spa.agendamiento.administrativo.service.trabajador.TrabajadorService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;

public class TrabajadorController implements Initializable {

    @FXML
    private Button btnNuevoTrabajador;

    @FXML
    private Button btnActualizarTrabajador;

    @FXML
    private Button btnEliminarTrabajador;

    @FXML
    private Button btnVolverMenu;

    @FXML
    private TableView<TrabajadorDetalle> tablaTrabajadores;

    @FXML
    private TableColumn<TrabajadorDetalle, String> colIdTrabajador;

    @FXML
    private TableColumn<TrabajadorDetalle, String> colNombre;

    @FXML
    private TableColumn<TrabajadorDetalle, String> colApellido;

    @FXML
    private TableColumn<TrabajadorDetalle, String> colOcupacion;

    @FXML
    private TableColumn<TrabajadorDetalle, String> colCorreoElectronico;

    private final TrabajadorService trabajadorService;

    public TrabajadorController() {
        trabajadorService = new TrabajadorService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarColumnas();

        tablaTrabajadores.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        cargarTrabajadores();
    }

    private void configurarColumnas() {

        colIdTrabajador.setCellValueFactory(
                new PropertyValueFactory<>("idTrabajador"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombreTrabajador"));

        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidoTrabajador"));

        colOcupacion.setCellValueFactory(
                new PropertyValueFactory<>("ocupacion"));

        colCorreoElectronico.setCellValueFactory(
                new PropertyValueFactory<>("trabajadorCorreoElectronico"));
    }

    public void cargarTrabajadores() {

        tablaTrabajadores.setItems(
                FXCollections.observableArrayList(
                        trabajadorService.listarTrabajadoresDetalle()
                )
        );
    }

    @FXML
    private void handleNuevoTrabajadorAction(ActionEvent event) {

        try {

            SceneManager sceneManager = new SceneManager(
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow()
            );

            FXMLLoader loader = sceneManager.abrirVentanaConControlador(
                    "/main/resources/view/trabajador/trabajador-form.fxml",
                    "Nuevo Trabajador"
            );

            TrabajadorFormController formController =
                    loader.getController();

            formController.setTrabajadorController(this);

        } catch (IOException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir el formulario de trabajador."
            );
        }
    }

    @FXML
    private void handleActualizarTrabajadorAction(ActionEvent event) {

        TrabajadorDetalle trabajadorSeleccionado =
                tablaTrabajadores.getSelectionModel().getSelectedItem();

        if (trabajadorSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Trabajador no seleccionado",
                    "Seleccione un trabajador para actualizar."
            );

            return;
        }

        try {

            Trabajador trabajador =
                    trabajadorService.buscarTrabajadorPorId(
                            trabajadorSeleccionado.getIdTrabajador()
                    );

            SceneManager sceneManager = new SceneManager(
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow()
            );

            FXMLLoader loader = sceneManager.abrirVentanaConControlador(
                    "/main/resources/view/trabajador/trabajador-form.fxml",
                    "Editar Trabajador"
            );

            TrabajadorFormController formController =
                    loader.getController();

            formController.setTrabajadorController(this);
            formController.prepararEdicion(trabajador);

        } catch (IOException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir el formulario de trabajador."
            );
        }
    }

    @FXML
    private void handleEliminarTrabajadorAction(ActionEvent event) {

        TrabajadorDetalle trabajadorSeleccionado =
                tablaTrabajadores.getSelectionModel().getSelectedItem();

        if (trabajadorSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Trabajador no seleccionado",
                    "Seleccione un trabajador para eliminar."
            );

            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar trabajador");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar al trabajador "
                + trabajadorSeleccionado.getNombreTrabajador()
                + " "
                + trabajadorSeleccionado.getApellidoTrabajador()
                + "?"
        );

        confirmacion.showAndWait().ifPresent(respuesta -> {

            if (respuesta == javafx.scene.control.ButtonType.OK) {

                String idTrabajador =
                        trabajadorSeleccionado.getIdTrabajador();

                if (trabajadorService.tieneCitasAsociadas(idTrabajador)) {

                    mostrarAlerta(
                            Alert.AlertType.WARNING,
                            "No se puede eliminar",
                            "El trabajador tiene citas asociadas. "
                            + "Debe eliminar primero las citas relacionadas."
                    );

                    return;
                }

                trabajadorService.eliminarTrabajador(idTrabajador);

                cargarTrabajadores();

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Trabajador eliminado",
                        "El trabajador se ha eliminado correctamente."
                );
            }
        });
    }

    @FXML
    private void handleVolverMenuAction(ActionEvent event) {

        try {

            SceneManager sceneManager = new SceneManager(
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow()
            );

            sceneManager.cambiarEscena(
                    "/main/resources/view/menuprincipal/menu-principal.fxml"
            );

        } catch (IOException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo regresar al menú principal."
            );
        }
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}