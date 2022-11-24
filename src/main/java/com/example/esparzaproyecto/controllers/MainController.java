package com.example.esparzaproyecto.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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


}
