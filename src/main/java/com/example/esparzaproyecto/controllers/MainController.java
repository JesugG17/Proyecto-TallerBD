package com.example.esparzaproyecto.controllers;

import com.example.esparzaproyecto.models.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainController {

    @FXML
    private TextField txtServidor, txtBaseDatos, txtInicioSesion, txtContrasenia;
    @FXML
    private Button btnCaptura, btnConsulta, btnConectar;
    @FXML
    private Label lblMensaje;

    @FXML
    public void connection() {

        if (!validarCampos()) {
            lblMensaje.setVisible( true );
            return;
        }


        String servidor = txtServidor.getText();
        String baseDatos = txtBaseDatos.getText();
        String inicioSesion = txtInicioSesion.getText();
        String contrasenia = txtContrasenia.getText();

        Conexion.conectar(servidor, baseDatos, inicioSesion, contrasenia);
        if (!Conexion.error().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR AL INICIAR SESION");
            alert.setContentText(Conexion.error());
            alert.showAndWait();
            return;
        }



        lblMensaje.setText("Conexion con exito");
        btnConectar.setVisible(false);
        lblMensaje.setVisible(true);
        btnCaptura.setVisible(true);
        btnConsulta.setVisible(true);
        txtServidor.setDisable(true);
        txtBaseDatos.setDisable(true);
        txtInicioSesion.setDisable(true);
        txtContrasenia.setDisable(true);
    }


    @FXML
    public void showCapturaView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/esparzaproyecto/captura-view.fxml"));
        try {

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Captura");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    @FXML
    public void showConsultaView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/esparzaproyecto/consulta-view.fxml"));
        try {

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Consulta");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private boolean validarCampos() {

        if (txtServidor.getText().isEmpty()) {
            txtServidor.requestFocus();
            lblMensaje.setText("Campo 'Servidor' vacio");
            return false;
        }

        if (txtBaseDatos.getText().isEmpty()) {
            txtBaseDatos.requestFocus();
            lblMensaje.setText("Campo 'Base de datos' vacio");
            return false;
        }

        if (txtInicioSesion.getText().isEmpty()) {
            txtInicioSesion.requestFocus();
            lblMensaje.setText("Campo 'Inicio de sesion' vacio");
            return false;
        }

        if (txtContrasenia.getText().isEmpty()) {
            txtContrasenia.requestFocus();
            lblMensaje.setText("Campo 'Contraseña' vacio");
            return false;
        }

        return true;
    }

}
