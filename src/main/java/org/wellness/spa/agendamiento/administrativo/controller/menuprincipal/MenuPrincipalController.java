package main.java.org.wellness.spa.agendamiento.administrativo.controller.menuprincipal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaResumen;
import main.java.org.wellness.spa.agendamiento.administrativo.service.cita.CitaService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;

public class MenuPrincipalController implements Initializable {

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lblBienvenida;

    @FXML
    private ImageView imgIcono;

    @FXML
    private Label lblDescripcionMenu;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnTratamientos;

    @FXML
    private Button btnCitas;

    @FXML
    private Button btnTrabajadores;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Label lblTituloTabla;

 @FXML
    private TableView<CitaResumen> tablaCitas;

    @FXML
    private TableColumn<CitaResumen, String> colNombreCliente;

    @FXML
    private TableColumn<CitaResumen, String> colApellidoCliente;

    @FXML
    private TableColumn<CitaResumen, String> colTrabajador;

    @FXML
    private TableColumn<CitaResumen, String> colTratamiento;

    @FXML
    private TableColumn<CitaResumen, String> colDescripcionTratamiento;

    @FXML
    private TableColumn<CitaResumen, LocalTime> colHora;

    @FXML
    private TableColumn<CitaResumen, LocalDate> colFecha;

    @FXML
    private TableColumn<CitaResumen, String> colEstado;

    private final CitaService citaService;

    public MenuPrincipalController() {
        citaService = new CitaService();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarCitas();
    }

    @FXML
    private void handleClientesAction(ActionEvent event) {
        
    }

    @FXML
    private void handleTratamientosAction(ActionEvent event) {
        
    }

    @FXML
    private void handleCitasAction(ActionEvent event) {
        SceneManager sceneManager = new SceneManager(
        (Stage)((Node) event.getSource()).getScene().getWindow()
        );
        
        try{
            sceneManager.cambiarEscena(
                    "/main/resources/view/cita/citas-view.fxml"
            );
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTrabajadoresAction(ActionEvent event) {
        SceneManager sceneManager = new SceneManager(
            (Stage)((Node) event.getSource()).getScene().getWindow()
        );

        try {
            sceneManager.cambiarEscena(
                    "/main/resources/view/trabajador/trabajadores-view.fxml"
            );
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCerrarSesionAction(ActionEvent event) {
            SceneManager sceneManager = new SceneManager(
                (Stage) ((Node) event.getSource()).getScene().getWindow()
        );

        try {
            sceneManager.cambiarEscena(
                    "/main/resources/view/login-view.fxml"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void configurarColumnas() {

        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));

        colTrabajador.setCellValueFactory(new PropertyValueFactory<>("trabajador"));

        colTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));

        colDescripcionTratamiento.setCellValueFactory(new PropertyValueFactory<>("descripcionTratamiento"));

        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }
    
    
    private void cargarCitas() {
        tablaCitas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tablaCitas.setItems(
                FXCollections.observableArrayList
                (citaService.listarCitasResumen()));
    }
    
    
}