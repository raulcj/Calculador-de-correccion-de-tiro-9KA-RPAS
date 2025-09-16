/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author rcabjai@mde.es
 */
public class Correccion9KARPASController implements Initializable {

    // Componentes
    @FXML
    private Button bCalcular; 
    @FXML
    private TextArea unidades_text; 
    // Datos de entrada
    @FXML
    private TextField x_9ka;
    @FXML
    private TextField y_9ka;
    @FXML
    private TextField r_obj;
    @FXML
    private TextField or_obj;
    @FXML
    private TextField x_rpas;
    @FXML
    private TextField y_rpas;
    // Resultados
    @FXML
    private TextField r_corr;
    @FXML
    private TextField or_corr;
   
    /***
     * Comprobar el formato valido del dato de entrada
     * 
     * @param s Cadena de texto
     */
    private static boolean isDouble(String s) 
    {
        if (s == null) return false; // Si no existe el valor
        try {
            double value = Double.parseDouble(s.trim());
            return Double.isFinite(value); // opcional: rechaza NaN/Infinity
        } catch (NumberFormatException e) {
            return false;
        }
    }
 
    /***
     * Inicializar el texto de informacion inferior
     *
     */
    private void init() 
    {
        unidades_text.setText("* Coordenadas X,Y (Grados decimales de longitud y latitud)\n"
                + "* Distancia al objetivo desde 9KA (metros)\n"
                + "* Orientación al objetivo desde 9KA (milésimas artilleras)");
    }
    
    /***
     * Realizar las transformaciones para calcular la correccion
     * 
     */
    @FXML
    protected void calcularCorreccion() 
    {
        if (isDouble(x_9ka.getText()) && isDouble(y_9ka.getText()) && 
                isDouble(r_obj.getText()) && isDouble(or_obj.getText()) &&
                isDouble(x_rpas.getText()) && isDouble(y_rpas.getText())
            ) 
        {
            // Valores de entrada
            double x_9ka_double = Double.parseDouble(x_9ka.getText());
            double y_9ka_double = Double.parseDouble(y_9ka.getText());
            double r_obj_double = Double.parseDouble(r_obj.getText());
            double or_obj_double = Double.parseDouble(or_obj.getText());
            double x_rpas_double = Double.parseDouble(x_rpas.getText());
            double y_rpas_double = Double.parseDouble(y_rpas.getText());
             // Valores intermedios
            double or_obj_rad = 0.0;
            double x_obj = 0.0;
            double y_obj = 0.0;
            double x_caida_9ka = 0.0;
            double y_caida_9ka = 0.0;
            double r_caida_9ka = 0.0;
            double theta = 0.0;
            double phi = 0.0;
            double or_caida_9ka = 0.0;
            double or_caida_9ka_mm = 0.0;
            // Valores finales
            double r_corr_double = 0.0;
            double or_corr_rad = 0.0;
            double or_corr_double = 0.0;
            double x_corr_double = 0.0;
            double y_corr_double = 0.0;                  
            // Transformaciones
            or_obj_rad = or_obj_double * PI / 3200;
            x_obj = r_obj_double * sin(or_obj_rad);
            y_obj = r_obj_double * cos(or_obj_rad);
            // Caida en sist.ref. 9KA
            x_caida_9ka = x_rpas_double - x_9ka_double;
            y_caida_9ka = y_rpas_double - y_9ka_double;
            r_caida_9ka = hypot(x_caida_9ka, y_caida_9ka);
            theta = atan2(y_caida_9ka, x_caida_9ka);
            phi = (PI / 2) - theta;        
            if (phi < 0) phi += 2 * PI;
            or_caida_9ka = phi;
            or_caida_9ka_mm = or_caida_9ka * 3200 / PI;
            // Correccion
            r_corr_double = r_caida_9ka - r_obj_double;
            or_corr_rad = or_caida_9ka - or_obj_rad;
            or_corr_double = or_corr_rad * 3200 / PI;
            x_corr_double = x_caida_9ka - x_obj;
            y_corr_double = y_caida_9ka - y_obj;
            // Setear resultados con redondeo de 4 decimales
            r_corr.setText(String.valueOf(Math.round(r_corr_double * 10000.0) / 10000.0));
            or_corr.setText(String.valueOf(Math.round(or_corr_double * 10000.0) / 10000.0));                         
        } 
        else
        {
            Alert alerta = new Alert(AlertType.WARNING);
            alerta.setTitle("ADVERTENCIA");
            alerta.setHeaderText("Error de formato");
            alerta.setContentText("Los datos introducidos no son válidos.");
            alerta.showAndWait();
        }
    } 
    
    /***
     * Comprobar que se reciben todos los parametros de entrada
     * 
     */
    private void bindCalcular() 
    {
        BooleanBinding missedData = Bindings.createBooleanBinding(
                () -> x_9ka.getText().trim().isEmpty()
                || y_9ka.getText().trim().isEmpty()
                || r_obj.getText().trim().isEmpty()
                || or_obj.getText().trim().isEmpty()
                || x_rpas.getText().trim().isEmpty()
                || y_rpas.getText().trim().isEmpty(),
                x_9ka.textProperty(), y_9ka.textProperty(), 
                r_obj.textProperty(), or_obj.textProperty(),
                x_rpas.textProperty(), y_rpas.textProperty());
        
        bCalcular.disableProperty().bind(missedData);            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        bindCalcular();
    }
    
}
