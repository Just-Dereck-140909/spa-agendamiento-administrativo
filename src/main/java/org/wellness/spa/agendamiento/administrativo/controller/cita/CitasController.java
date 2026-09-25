package main.java.org.wellness.spa.agendamiento.administrativo.controller.cita;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.Cita;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaDetalle;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cita.CitaService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;

public class CitasController implements Initializable {

    
    public CitasController() {
        citaService = new CitaService();
    }
    
    private final CitaService citaService;
        
    //Etiquetas para los objetos del FXML
    @FXML
    private Button btnNuevaCita;

    @FXML
    private Button btnActualizarCita;

    @FXML
    private Button btnEliminarCita;

    @FXML
    private Button btnVolverMenu;

    @FXML
    private TableView<CitaDetalle> tablaCitas;

    @FXML
    private TableColumn<CitaDetalle, String> colIdCita;

    @FXML
    private TableColumn<CitaDetalle, String> colCliente;

    @FXML
    private TableColumn<CitaDetalle, String> colTrabajador;

    @FXML
    private TableColumn<CitaDetalle, String> colTratamiento;

    @FXML
    private TableColumn<CitaDetalle, LocalDate> colFecha;

    @FXML
    private TableColumn<CitaDetalle, LocalTime> colHora;

    @FXML
    private TableColumn<CitaDetalle, String> colEstado;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarColumnas();

        tablaCitas.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        cargarCitas();
    }
    
    
    @FXML
    private void handleNuevaCitaAction(ActionEvent event) {

        SceneManager sceneManager = new SceneManager(
                (Stage) ((Node) event.getSource()).getScene().getWindow()
        );

        try {
            FXMLLoader loader = sceneManager.abrirVentanaConControlador(
            "/main/resources/view/cita/cita-form.fxml",
            "Nueva Cita"
        );

        CitaFormController controller = loader.getController();
        controller.setCitasController(this);
            } catch (IOException e) {
            }
    }

    
    @FXML
    private void handleActualizarCitaAction(ActionEvent event) {

        CitaDetalle citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Cita no seleccionada",
                    "Debe seleccionar una cita para actualizar."
            );
            return;
        }

        Cita cita = citaService.buscarCitaPorId(
                citaSeleccionada.getIdCita()
        );

        if (cita == null) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se encontró la cita seleccionada."
            );
            return;
        }

        
        SceneManager sceneManager = new SceneManager(
                (Stage) ((Node) event.getSource()).getScene().getWindow());

        try {
            FXMLLoader loader = sceneManager.abrirVentanaConControlador(
                    "/main/resources/view/cita/cita-form.fxml",
                    "Actualizar Cita");

            CitaFormController controller = loader.getController();
            controller.setCitasController(this);
            controller.prepararEdicion(cita);

        } catch (IOException e) {
        }
    }

    
    @FXML
    private void handleEliminarCitaAction(ActionEvent event) {

        CitaDetalle citaSeleccionada =
                tablaCitas.getSelectionModel().getSelectedItem();

        if (citaSeleccionada == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Cita no seleccionada",
                    "Debe seleccionar una cita para eliminar."
            );
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar cita");
        alerta.setHeaderText("¿Está seguro de eliminar esta cita?");
        alerta.setContentText(
                "Cita: " + citaSeleccionada.getIdCita()
                + "\nCliente: " + citaSeleccionada.getCliente()
                + "\nFecha: " + citaSeleccionada.getFecha()
                + "\nHora: " + citaSeleccionada.getHora()
        );

        if (alerta.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {

            try {

                citaService.eliminarCita(
                        citaSeleccionada.getIdCita()
                );

                cargarCitas();

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Cita eliminada",
                        "La cita se ha eliminado correctamente."
                );

            } catch (Exception e) {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error al eliminar",
                        "No se pudo eliminar la cita."
                );
            }
        }
    }
    

    @FXML
    private void handleVolverMenuAction(ActionEvent event) {
        SceneManager sceneManager = new SceneManager(
                (Stage) ((Node) event.getSource()).getScene().getWindow()
        );

        try {

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
    
    
    //Metodos de funcionamiento
    
    public void cargarCitas() {

        tablaCitas.setItems(
                FXCollections.observableArrayList(
                citaService.listarCitasDetalle())
                );
    }
    
    private void configurarColumnas() {

        colIdCita.setCellValueFactory(
            new PropertyValueFactory<>("idCita"));

    colCliente.setCellValueFactory(
            new PropertyValueFactory<>("cliente"));

    colTrabajador.setCellValueFactory(
            new PropertyValueFactory<>("trabajador"));

    colTratamiento.setCellValueFactory(
            new PropertyValueFactory<>("tratamiento"));

    colFecha.setCellValueFactory(
            new PropertyValueFactory<>("fecha"));

    colHora.setCellValueFactory(
            new PropertyValueFactory<>("hora"));

    colEstado.setCellValueFactory(
            new PropertyValueFactory<>("estado"));
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
   
    
}