package main.java.org.wellness.spa.agendamiento.administrativo.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void cambiarEscena(String rutaFXML) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(rutaFXML)
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    
    
    public void abrirVentana(String rutaFXML, String titulo) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(rutaFXML)
        );

        Parent root = loader.load();

        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle(titulo);
        nuevaVentana.setScene(new Scene(root));

        nuevaVentana.show();
    }
    
    
    public FXMLLoader abrirVentanaConControlador(String rutaFXML, String titulo) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(rutaFXML));

        Parent root = loader.load();

        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle(titulo);
        nuevaVentana.setScene(new Scene(root));

        nuevaVentana.show();

        return loader;
    }
}