package main.java.org.wellness.spa.agendamiento.administrativo.controller.cita;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.Cita;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.OpcionComboBox;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cita.CitaFormService;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cita.CitaService;
import main.java.org.wellness.spa.agendamiento.administrativo.controller.cita.CitasController;

public class CitaFormController implements Initializable {

    private boolean modoEdicion = false;
    private Cita citaEditar;
    
    private final CitaFormService citaFormService;
    private final CitaService citaService;
    private CitasController citasController;
    
    
    public CitaFormController() {
        citaFormService = new CitaFormService();
        citaService = new CitaService();
    }
    
    //Etiquetas para los objetos del FXML
    @FXML
    private Label lblTituloFormulario;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private ComboBox<LocalTime> cbHora;

    @FXML
    private ComboBox<OpcionComboBox> cbCliente;

    @FXML
    private ComboBox<OpcionComboBox> cbTrabajador;

    @FXML
    private ComboBox<OpcionComboBox> cbTratamiento;

    @FXML
    private ComboBox<String> cbEstado;
    
    @FXML
    private Button btnCerrar;
    
    @FXML
    private Button btnGuardar;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarHoras();
        cargarClientes();
        cargarTrabajadores();
        cargarTratamientos();
        
        Platform.runLater(() -> {

            Stage stage = (Stage) btnCerrar.getScene().getWindow();

            stage.setOnCloseRequest(event -> {

                if (!confirmarCierre()) {
                    event.consume();
                }
            });
        });
        
    }    
    
    @FXML
    private void handleGuardarAction(ActionEvent event) {

        // Validar fecha
        if (dpFecha.getValue() == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Fecha requerida",
                    "Debe seleccionar una fecha para la cita."
            );
            return;
        }

        if (dpFecha.getValue().isBefore(LocalDate.now())) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Fecha inválida",
                    "La fecha de la cita no puede ser anterior al día actual."
            );
            return;
        }

        // Validar hora
        if (cbHora.getValue() == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Hora requerida",
                    "Debe seleccionar una hora para la cita."
            );
            return;
        }

        // Validar cliente
        if (cbCliente.getValue() == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Cliente requerido",
                    "Debe seleccionar un cliente."
            );
            return;
        }

        // Validar trabajador
        if (cbTrabajador.getValue() == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Trabajador requerido",
                    "Debe seleccionar un trabajador."
            );
            return;
        }

        // Validar tratamiento
        if (cbTratamiento.getValue() == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Tratamiento requerido",
                    "Debe seleccionar un tratamiento."
            );
            return;
        }

        
        // Crear la cita/actualizar
        Cita cita;

        if (modoEdicion) {

            cita = new Cita(
                    citaEditar.getIdCita(),
                    dpFecha.getValue(),
                    cbHora.getValue(),
                    cbEstado.getValue(),
                    citaEditar.getIdCliente(),
                    cbTrabajador.getValue().getId(),
                    cbTratamiento.getValue().getId()
            );

        } else {

            cita = new Cita(
                    null,
                    dpFecha.getValue(),
                    cbHora.getValue(),
                    null,
                    cbCliente.getValue().getId(),
                    cbTrabajador.getValue().getId(),
                    cbTratamiento.getValue().getId()
            );
        }

        try {
            if (modoEdicion){
                
                citaService.actualizarCita(cita);

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Cita actualizada",
                        "La cita se ha actualizado correctamente."
                );
                        
            } else {
                
                citaService.guardarCita(cita);
                
                mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Cita guardada",
                    "La cita se ha registrado correctamente."
                );
                
                
            }
            
            if (citasController != null) {
                
                citasController.cargarCitas();
            }

            cerrarVentana(event);

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    modoEdicion ? "Error al actualizar" : "Error al guardar",
                    modoEdicion ?"No se pudo actualizar la cita." : "No se pudo guardarla cita"
            );

        }
    }

    @FXML
    private void handleCerrarAction(ActionEvent event) {

        if (confirmarCierre()) {
            cerrarVentana(event);
        }
    }
    
    
    
    
    
    //Metodos de funcionamiento
    
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

        return dpFecha.getValue() != null
                || cbHora.getValue() != null
                || cbCliente.getValue() != null
                || cbTrabajador.getValue() != null
                || cbTratamiento.getValue() != null;
    }
    
    
    private void cargarHoras() {

        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFin = LocalTime.of(17, 0);

        while (!horaInicio.isAfter(horaFin)) {

            cbHora.getItems().add(horaInicio);

            horaInicio = horaInicio.plusMinutes(15);
        }
    }
    
    
    private void cargarClientes() {

        cbCliente.setItems(
                FXCollections.observableArrayList(
                        citaFormService.listarClientes()
                )
        );
    }
    
    
    private void cargarTrabajadores() {

        cbTrabajador.setItems(
                FXCollections.observableArrayList(
                        citaFormService.listarTrabajadores()
                )
        );
    }
    
    
    private void cargarTratamientos() {

        cbTratamiento.setItems(
                FXCollections.observableArrayList(
                        citaFormService.listarTratamientos()
                )
        );
    }
    
    
    private void cerrarVentana(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.close();
    }
    
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public void setCitasController(CitasController citasController) {
        this.citasController = citasController;
    }
    
    public void prepararEdicion(Cita cita) {
        this.modoEdicion = true;
        this.citaEditar = cita;
        
        dpFecha.setValue(cita.getFechaCita());
        cbHora.setValue(cita.getHoraCita());
        
        cbCliente.setDisable(true);
        for (OpcionComboBox opcion : cbCliente.getItems()) {
            if (opcion.getId().equals(cita.getIdCliente())) {
                cbCliente.setValue(opcion);
                break;
            }
        }

                for (OpcionComboBox opcion : cbTrabajador.getItems()) {
            if (opcion.getId().equals(cita.getIdTrabajador())) {
                cbTrabajador.setValue(opcion);
                break;
            }
        }

        for (OpcionComboBox opcion : cbTratamiento.getItems()) {
            if (opcion.getId().equals(cita.getIdTratamiento())) {
                cbTratamiento.setValue(opcion);
                break;
            }
        }

        cbEstado.setDisable(false);
        cbEstado.setItems(FXCollections.observableArrayList(
                "Pendiente",
                "Confirmada",
                "Completada",
                "Cancelada"
        ));
        cbEstado.setValue(cita.getEstadoCita());
    }
    
    
    
}
