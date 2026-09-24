package main.java.org.wellness.spa.agendamiento.administrativo.controller.tratamiento;

import java.math.BigDecimal;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.java.org.wellness.spa.agendamiento.administrativo.model.tratamiento.Tratamiento;
import main.java.org.wellness.spa.agendamiento.administrativo.service.tratamiento.TratamientoService;

public class TratamientoFormController {

    @FXML
    private Label lblTituloFormulario;

    @FXML
    private TextField txtNombreTratamiento;

    @FXML
    private TextField txtCostoTratamiento;

    @FXML
    private TextField txtDescripcionTratamiento;

    @FXML
    private ComboBox<String> cbDuracionTratamiento;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCerrar;

    private final TratamientoService tratamientoService;

    private Tratamiento tratamientoEditar;

    public TratamientoFormController() {
        tratamientoService = new TratamientoService();
    }

    @FXML
    private void initialize() {

        cbDuracionTratamiento.setItems(FXCollections.observableArrayList(
                "30 minutos",
                "60 minutos",
                "90 minutos",
                "120 minutos",
                "150 minutos",
                "180 minutos"
        ));
    }

    public void cargarTratamiento(Tratamiento tratamiento) {

        tratamientoEditar = tratamiento;

        lblTituloFormulario.setText("Editar Tratamiento");

        txtNombreTratamiento.setText(tratamiento.getNombreTratamiento());
        txtCostoTratamiento.setText(tratamiento.getCostoTratamiento().toString());
        txtDescripcionTratamiento.setText(tratamiento.getDescripcionTratamiento());

        cbDuracionTratamiento.setValue(
                tratamiento.getDuracionTratamiento() + " minutos"
        );
    }

    @FXML
    private void handleGuardarAction() {

        if (!validarCampos()) {
            return;
        }

        try {

            String nombre = txtNombreTratamiento.getText().trim();
            BigDecimal costo = new BigDecimal(txtCostoTratamiento.getText().trim());
            String descripcion = txtDescripcionTratamiento.getText().trim();

            int duracion = Integer.parseInt(
                    cbDuracionTratamiento.getValue().split(" ")[0]
            );

            if (tratamientoEditar == null) {

                Tratamiento tratamiento = new Tratamiento(
                        null,
                        nombre,
                        costo,
                        descripcion,
                        duracion
                );

                tratamientoService.guardar(tratamiento);

                mostrarMensaje(
                        Alert.AlertType.INFORMATION,
                        "Tratamiento guardado",
                        "El tratamiento se guardó correctamente."
                );

            } else {

                tratamientoEditar.setNombreTratamiento(nombre);
                tratamientoEditar.setCostoTratamiento(costo);
                tratamientoEditar.setDescripcionTratamiento(descripcion);
                tratamientoEditar.setDuracionTratamiento(duracion);

                tratamientoService.actualizar(tratamientoEditar);

                mostrarMensaje(
                        Alert.AlertType.INFORMATION,
                        "Tratamiento actualizado",
                        "El tratamiento se actualizó correctamente."
                );
            }

            cerrarFormulario();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "El costo debe ser un número válido."
            );
        }
    }

    @FXML
    private void handleCerrarAction() {

        boolean hayDatos =
                !txtNombreTratamiento.getText().trim().isEmpty()
                || !txtCostoTratamiento.getText().trim().isEmpty()
                || !txtDescripcionTratamiento.getText().trim().isEmpty()
                || cbDuracionTratamiento.getValue() != null;

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

            if (resultado.isPresent() && resultado.get() == botonSi) {
                cerrarFormulario();
            }

        } else {

            cerrarFormulario();
        }
    }

    private boolean validarCampos() {

        if (txtNombreTratamiento.getText().trim().isEmpty()) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Debe ingresar el nombre del tratamiento."
            );

            return false;
        }

        if (txtCostoTratamiento.getText().trim().isEmpty()) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Debe ingresar el costo del tratamiento."
            );

            return false;
        }

        if (txtDescripcionTratamiento.getText().trim().isEmpty()) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Debe ingresar la descripción del tratamiento."
            );

            return false;
        }

        if (cbDuracionTratamiento.getValue() == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "Debe seleccionar una duración."
            );

            return false;
        }

        return true;
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

    private void cerrarFormulario() {

        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}