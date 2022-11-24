package com.example.esparzaproyecto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class MainController {

    @FXML
    private TextField txtServidor, txtBaseDatos, txtInicioSesion , txtContrasenia;
    @FXML
    private Button btnCaptura, btnConsulta;
    @FXML
    private Label lblMensaje;
    private Connection coneccion;

   @FXML public void connection(){

        String servidor = txtServidor.getText();
        String baseDatos = txtBaseDatos.getText();
        String inicioSesion = txtInicioSesion.getText();
        String contrasenia = txtContrasenia.getText();
        String url = "jdbc:sqlserver://" + servidor + ";databaseName="+ baseDatos +";encrypt=true;trustServerCertificate=true";
        try {
            coneccion = DriverManager.getConnection(url, inicioSesion, contrasenia);
            lblMensaje.setText("Coneccion con exito");
            lblMensaje.setVisible(true);
            btnCaptura.setVisible(true);
            btnConsulta.setVisible(true);
            txtServidor.setDisable(true);
            txtBaseDatos.setDisable(true);
            txtInicioSesion.setDisable(true);
            txtContrasenia.setDisable(true);
        }catch (Exception e){
            lblMensaje.setText("Algun dato es incorrecto");
            lblMensaje.setVisible(true);
            System.out.println(e.getMessage());
        }
    }


    @FXML public void showCapturaView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/esparzaproyecto/captura-view.fxml"));
        try {

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Captura");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable( false );
            stage.setScene(scene);
            stage.showAndWait();

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

}
