package com.example.esparzaproyecto.controllers;

import com.example.esparzaproyecto.models.Articulo;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {

    @FXML
    RadioButton radioNuevo, radioModificar, radioEliminar;

    @FXML
    ComboBox<String> cmbFamilias;

    @FXML
    TableView<Articulo> tablaDatos;

    @FXML
    TableColumn<String, Articulo> columnClave, columnNombre, columnDescripcion, columnPrecio, columnFamilia;

    @FXML
    TextField txtClave, txtNombre, txtDescripcion, txtPrecio;

    @FXML
    ObservableList<Articulo> articulos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        articulos = FXCollections.observableArrayList();
        radioNuevo.setToggleGroup( toggleGroup );
        radioNuevo.setSelected( true );
        radioModificar.setToggleGroup( toggleGroup );
        radioEliminar.setToggleGroup( toggleGroup );

        tablaDatos.setPlaceholder(new Label("Sin articulos que mostrar"));
        tablaDatos.setItems( articulos );

        columnClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));

        llenarCombo();
    }


    @FXML
    public void grabar() {

        int clave = 1;
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int famId = Integer.parseInt(cmbFamilias.getValue());

        Articulo articulo = new Articulo(clave, nombre, descripcion, precio, famId);

        articulos.add(articulo);
        tablaDatos.refresh();

    }

    @FXML
    public void limpiar() {
        txtClave.setText("*");
        txtClave.setDisable( true );
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        radioNuevo.setSelected( true );
        llenarCombo();
    }

    private String saberQuery() {
        StringBuilder query = new StringBuilder();
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        String precio = txtDescripcion.getText();
        String famId = cmbFamilias.getValue();

        if ( !radioNuevo.isDisable() ) {
            query.append("insert articulos values(");
        }

        if ( !radioModificar.isDisable() ) {
            query.append("update articulos set ");
        }

        if ( !radioEliminar.isDisable() ) {
            query.append("delete articulos where");
        }

        return query.toString();

    }

    private boolean validarDatos() {
        return false;
    }

    private void llenarCombo() {

        String query = "select famId from familias";
        cmbFamilias.getItems().clear();
        try {
            MainController.hacerConexion();
            Statement smt = MainController.coneccion.createStatement();
            ResultSet tuplas = smt.executeQuery( query );

            while( tuplas.next() ) {
                cmbFamilias.getItems().add( tuplas.getInt("famId") + "");
            }


        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

    }


}
