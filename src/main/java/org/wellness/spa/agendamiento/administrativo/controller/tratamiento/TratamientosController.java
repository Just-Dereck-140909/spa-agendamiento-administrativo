package main.java.org.wellness.spa.agendamiento.administrativo.controller.tratamiento;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.java.org.wellness.spa.agendamiento.administrativo.model.tratamiento.Tratamiento;
import main.java.org.wellness.spa.agendamiento.administrativo.service.tratamiento.TratamientoService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;

public class TratamientosController {

    @FXML
    private Button btnNuevoTratamientos;

    @FXML
    private Button btnActualizarTratamientos;

    @FXML
    private Button btnEliminarTratamientos;

    @FXML
    private Button btnVolverMenu;

    @FXML
    private TableView<Tratamiento> tablaTratamientos;

    @FXML
    private TableColumn<Tratamiento, String> colIdTratamiento;

    @FXML
    private TableColumn<Tratamiento, String> colNombreTratamiento;

    @FXML
    private TableColumn<Tratamiento, java.math.BigDecimal> colCostoTratamiento;

    @FXML
    private TableColumn<Tratamiento, String> colDescripcionTratamiento;

    @FXML
    private TableColumn<Tratamiento, Integer> colDuracionTratamiento;

    private final TratamientoService tratamientoService;

    public TratamientosController() {
        tratamientoService = new TratamientoService();
    }

    @FXML
    private void initialize() {

        configurarColumnas();
        cargarTratamientos();
    }

    private void configurarColumnas() {

        colIdTratamiento.setCellValueFactory(
                new PropertyValueFactory<>("idTratamiento")
        );

        colNombreTratamiento.setCellValueFactory(
                new PropertyValueFactory<>("nombreTratamiento")
        );

        colCostoTratamiento.setCellValueFactory(
                new PropertyValueFactory<>("costoTratamiento")
        );

        colDescripcionTratamiento.setCellValueFactory(
                new PropertyValueFactory<>("descripcionTratamiento")
        );

        colDuracionTratamiento.setCellValueFactory(
                new PropertyValueFactory<>("duracionTratamiento")
        );
    }

    private void cargarTratamientos() {

        tablaTratamientos.setItems(
                FXCollections.observableArrayList(
                        tratamientoService.listar()
                )
        );
    }

    @FXML
    private void handleNuevoTratamientosAction() {

        abrirFormulario(null);
    }

    @FXML
    private void handleActualizarTratamientosAction() {

        Tratamiento tratamientoSeleccionado =
                tablaTratamientos.getSelectionModel().getSelectedItem();

        if (tratamientoSeleccionado == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Tratamiento no seleccionado",
                    "Seleccione un tratamiento para actualizar."
            );

            return;
        }

        abrirFormulario(tratamientoSeleccionado);
    }

    @FXML
    private void handleEliminarTratamientosAction() {

        Tratamiento tratamientoSeleccionado =
                tablaTratamientos.getSelectionModel().getSelectedItem();

        if (tratamientoSeleccionado == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Tratamiento no seleccionado",
                    "Seleccione un tratamiento para eliminar."
            );

            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar tratamiento");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar el tratamiento \""
                + tratamientoSeleccionado.getNombreTratamiento()
                + "\"?"
        );

       confirmacion.showAndWait().ifPresent(respuesta -> {

    if (respuesta == javafx.scene.control.ButtonType.OK) {

        tratamientoService.eliminar(
                tratamientoSeleccionado.getIdTratamiento()
        );

        cargarTratamientos();
    }
});
    }

    private void abrirFormulario(Tratamiento tratamiento) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/main/resources/view/tratamiento/tratamiento-form.fxml"
                    )
            );

            Parent root = loader.load();

            TratamientoFormController controller =
                    loader.getController();

            if (tratamiento != null) {
                controller.cargarTratamiento(tratamiento);
            }

            Stage stage = new Stage();

            stage.setTitle(
                    tratamiento == null
                    ? "Nuevo Tratamiento"
                    : "Editar Tratamiento"
            );

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarTratamientos();

        } catch (IOException e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir el formulario de tratamiento."
            );

            System.out.println(
                    "Error al abrir el formulario de tratamiento: "
                    + e.getMessage()
            );
        }
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
 
            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo regresar al menú principal."
            );
        }
    }
    

    private void mostrarMensaje(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}