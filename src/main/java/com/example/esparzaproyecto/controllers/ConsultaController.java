package com.example.esparzaproyecto.controllers;

import com.example.esparzaproyecto.models.Articulo;
import com.example.esparzaproyecto.models.Familia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    ObservableList<Articulo> articulos;
    @FXML
    TableView<Familia> tablaDatos;
    @FXML
    ObservableList<Familia> vw_familias;

    @FXML
    TableColumn<String, Familia> columnFamID, columnFamilia, columnArtID, columnArticulo, columnDescripcion, columnPrecio;

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

    private void llenarTabla() {
        try {
            Familia familia;
            Statement smt = MainController.coneccion.createStatement();
            ResultSet tuplas = smt.executeQuery("select * from vw_familias");
            while ( tuplas.next() ) {
                familia = new Familia(tuplas.getInt("famid"),
                        tuplas.getString("famnombre"),
                        tuplas.getInt("artid"),
                        tuplas.getString("artnombre"),
                        tuplas.getString("artdescripcion"),
                        tuplas.getDouble("artprecio"));
                vw_familias.add( familia );
            }
            tablaDatos.refresh();
        } catch(Exception error) {
            System.out.println(error.getMessage());
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
        vw_familias = FXCollections.observableArrayList();

        tablaDatos.setPlaceholder(new Label("Sin articulos que mostrar"));
        tablaDatos.setItems( vw_familias );

        columnArtID.setCellValueFactory(new PropertyValueFactory<>("artId"));
        columnArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        columnFamID.setCellValueFactory(new PropertyValueFactory<>("famId"));

        llenarTabla();
        cargarFamilias();
    }
}
