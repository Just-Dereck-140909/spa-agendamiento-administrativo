package main.java.org.wellness.spa.agendamiento.administrativo.controller.recepcionista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.org.wellness.spa.agendamiento.administrativo.model.recepcionista.Recepcionista;
import main.java.org.wellness.spa.agendamiento.administrativo.service.recepcionista.RecepcionistaService;
import main.java.org.wellness.spa.agendamiento.administrativo.util.SceneManager;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    private final RecepcionistaService recepcionistaService;

    public LoginController() {
        recepcionistaService = new RecepcionistaService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización del controlador
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {

        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isBlank() || contrasena.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wellness Spa");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();

            return;
        }

        Recepcionista recepcionista =
                recepcionistaService.iniciarSesion(usuario, contrasena);

        if (recepcionista != null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wellness Spa");
            alert.setHeaderText("Inicio de sesión exitoso");
            alert.setContentText(
                    "¡Bienvenido, "
                    + recepcionista.getNombreRecepcionista()
                    + "!"
            );
            alert.showAndWait();

            SceneManager sceneManager = new SceneManager(
            (Stage) ((Node) event.getSource()).getScene().getWindow()
            );

            try {
                sceneManager.cambiarEscena(
                        "/main/resources/view/menu-principal.fxml"
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wellness Spa");
            alert.setHeaderText("Error de autenticación");
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    @FXML
    private void salir(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wellness Spa");
        alert.setHeaderText("Cerrar aplicación");
        alert.setContentText("¿Está seguro de que desea salir?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            System.exit(0);
        }
    }
}