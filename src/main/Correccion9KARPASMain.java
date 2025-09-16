/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author rcabjai@mde.es
 */
public class Correccion9KARPASMain extends Application {
    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Correccion9KARPASMain.class.getResource("/view/Correccion9KARPASView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calculador de corrección de tiro 9KA-RPAS v1.0.0");       
        Image icono = new Image("/images/drone-color.png");   
        stage.getIcons().add(icono);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
  }

    public static void main(String[] args) {
        launch();
    }
    
}
