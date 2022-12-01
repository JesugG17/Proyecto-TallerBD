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
    TextField txtClave, txtNombre, txtDescripcion, txtPrecio;
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
        Familia familia;
        vw_familias.clear();
        try {
            Statement state = coneccion.createStatement();
            String query = hacerQuery();
            ResultSet tuplas = state.executeQuery(query);
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

        }catch (Exception e){
            System.out.println("que menso no jala");
            System.out.println(e.getMessage());
        }

    }
    @FXML
    public void limpiar(){
        txtClave.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        cmbFamilias.getSelectionModel().clearSelection();
        txtPrecio.setText("");
    }


    public String hacerQuery(){
        String  condiciones = " WHERE ";
        String query = "SELECT * FROM vw_familias ";
        if(!txtClave.getText().isEmpty()){
            query += condiciones + " artid = " + txtClave.getText();
            condiciones = " AND ";
        }
        if(!txtNombre.getText().isEmpty()){
            query += condiciones + "artnombre like '%" + txtNombre.getText() + "%'";
            condiciones = " AND ";
        }
        if(!txtDescripcion.getText().isEmpty()){
            query += condiciones + "artdescripcion like '%"+txtDescripcion.getText() +"%'";
            condiciones = " AND ";
        }
        if(cmbFamilias.getSelectionModel().getSelectedIndex() > 0){
            query += condiciones + "famid = " + cmbFamilias.getSelectionModel().getSelectedItem();
            condiciones = " AND ";
        }
        if(!txtPrecio.getText().isEmpty()){
            int precio = Integer.parseInt(txtPrecio.getText());
            query += condiciones + "artprecio > " + precio + " AND artprecio < " + (precio + 1);
        }

        System.out.println(query);
        return query;
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
