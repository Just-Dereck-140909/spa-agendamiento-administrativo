package main.java.org.wellness.spa.agendamiento.administrativo.controller.trabajador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Optional;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import main.java.org.wellness.spa.agendamiento.administrativo.model.ocupacion.Ocupacion;
import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.Trabajador;
import main.java.org.wellness.spa.agendamiento.administrativo.service.trabajador.TrabajadorService;
import main.java.org.wellness.spa.agendamiento.administrativo.service.trabajador.TrabajadorFormService;

public class TrabajadorFormController implements Initializable {

    @FXML
    private Label lblTituloFormulario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private ComboBox<Ocupacion> cbOcupacion;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCerrar;

    private final TrabajadorService trabajadorService;
    private final TrabajadorFormService trabajadorFormService;

    private Trabajador trabajadorEditar;
    private TrabajadorController trabajadorController;

    private boolean modoEdicion = false;

    public TrabajadorFormController() {
        trabajadorService = new TrabajadorService();
        trabajadorFormService = new TrabajadorFormService();
    }
    
    public void setTrabajadorController(
            TrabajadorController trabajadorController) {

        this.trabajadorController = trabajadorController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarOcupaciones();

        Platform.runLater(() -> {

            Stage stage = (Stage) btnCerrar.getScene().getWindow();

            stage.setOnCloseRequest(event -> {

                if (!confirmarCierre()) {
                    event.consume();
                }
            });
        });
    }
    
    

    private void cargarOcupaciones() {

        List<Ocupacion> ocupaciones =
                trabajadorFormService.listarOcupaciones();

        cbOcupacion.setItems(
                FXCollections.observableArrayList(ocupaciones)
        );

        cbOcupacion.setConverter(
                new javafx.util.StringConverter<Ocupacion>() {

                    @Override
                    public String toString(Ocupacion ocupacion) {
                        return ocupacion == null
                                ? ""
                                : ocupacion.getNombreOcupacion();
                    }

                    @Override
                    public Ocupacion fromString(String string) {
                        return null;
                    }
                }
        );
    }

    @FXML
    private void handleGuardarAction() {

        if (!validarCampos()) {
            return;
        }

        try {

            if (modoEdicion) {

                actualizarTrabajador();

            } else {

                crearTrabajador();
            }

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Ocurrió un error al guardar el trabajador."
            );
        }
    }

    private void crearTrabajador() {

        Trabajador trabajador = new Trabajador();

        trabajador.setNombreTrabajador(
                txtNombre.getText().trim()
        );

        trabajador.setApellidoTrabajador(
                txtApellido.getText().trim()
        );

        trabajador.setIdOcupacion(
                cbOcupacion.getValue().getIdOcupacion()
        );

        trabajadorService.guardarTrabajador(trabajador);

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Trabajador creado",
                "El trabajador se ha registrado correctamente.\n\n"
                + "ID: " + trabajador.getIdTrabajador()
                + "\nCorreo: "
                + trabajador.getTrabajadorCorreoElectronico()
        );

        actualizarTabla();

        cerrarVentana();
    }

    private void actualizarTrabajador() {

        trabajadorEditar.setNombreTrabajador(
                txtNombre.getText().trim()
        );

        trabajadorEditar.setApellidoTrabajador(
                txtApellido.getText().trim()
        );

        trabajadorEditar.setIdOcupacion(
                cbOcupacion.getValue().getIdOcupacion()
        );

        trabajadorService.actualizarTrabajador(trabajadorEditar);

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Trabajador actualizado",
                "El trabajador se ha actualizado correctamente."
        );

        actualizarTabla();

        cerrarVentana();
    }

    private boolean validarCampos() {

        if (txtNombre.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Ingrese el nombre del trabajador."
            );

            txtNombre.requestFocus();
            return false;
        }

        if (txtApellido.getText().trim().isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Ingrese el apellido del trabajador."
            );

            txtApellido.requestFocus();
            return false;
        }

        if (cbOcupacion.getValue() == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Seleccione una ocupación."
            );

            cbOcupacion.requestFocus();
            return false;
        }

        return true;
    }

    public void prepararEdicion(Trabajador trabajador) {

        modoEdicion = true;
        trabajadorEditar = trabajador;

        lblTituloFormulario.setText("Editar Trabajador");

        txtNombre.setText(
                trabajador.getNombreTrabajador()
        );

        txtApellido.setText(
                trabajador.getApellidoTrabajador()
        );

        for (Ocupacion ocupacion : cbOcupacion.getItems()) {

            if (ocupacion.getIdOcupacion().equals(
                    trabajador.getIdOcupacion())) {

                cbOcupacion.setValue(ocupacion);
                break;
            }
        }
    }


    private void actualizarTabla() {

        if (trabajadorController != null) {
            trabajadorController.cargarTrabajadores();
        }
    }

    @FXML
    private void handleCerrarAction() {

        if (confirmarCierre()) {
            cerrarVentana();
        }
    }
    
    
    private boolean confirmarCierre() {

        if (!hayDatosIngresados()) {
            return true;
        }

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

        return resultado.isPresent()
                && resultado.get() == botonSi;
    }
        
        
    private boolean hayDatosIngresados() {

        return !txtNombre.getText().trim().isEmpty()
                || !txtApellido.getText().trim().isEmpty()
                || cbOcupacion.getValue() != null;
    }

    private void cerrarVentana() {

        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}