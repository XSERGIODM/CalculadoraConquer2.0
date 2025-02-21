package org.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.util.Path_Util;

public class App_Application extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App_Application.class.getResource(Path_Util.CALCULADORA));

            // Cargo la ventana
            Pane ventana = loader.load();

            // Cargo el scene
            Scene scene = new Scene(ventana);

            // Establezco el ícono de la ventana
            primaryStage.getIcons().add(new Image(Path_Util.ICON_DB));
            primaryStage.setTitle("Calculadora - Conquista 2.0");
            primaryStage.setResizable(false);

            // Seteo la scene y la muestro
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
