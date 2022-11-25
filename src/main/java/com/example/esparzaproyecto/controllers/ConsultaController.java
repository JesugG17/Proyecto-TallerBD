package com.example.esparzaproyecto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ConsultaController implements Initializable {

    Connection coneccion = MainController.coneccion;
    @FXML
    ComboBox cmbFamilias;
    @FXML
    TextField txtClave;

    @FXML
    public void busquedaClave(){
        String busqueda = txtClave.getText();
        try {
            Statement state = coneccion.createStatement();
            String query = "SELECT * from familias " ;
            ResultSet res = state.executeQuery(query);
            while (res.next()){
            }

        }catch (Exception e){
            System.out.println("que menso no jala");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void cargarFamilias(){
        try {
            Statement state = coneccion.createStatement();
            String query = "SELECT * from familias " ;
            ResultSet res = state.executeQuery(query);
            while (res.next())
                cmbFamilias.getItems().add(res.getInt("famid") + "");
        }catch (Exception e){
            System.out.println("que menso no jala");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarFamilias();
    }
}
