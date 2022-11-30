package com.example.esparzaproyecto.controllers;

import com.example.esparzaproyecto.models.Articulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
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

    @FXML
    Label labelMessages;

    private String msg;


    /*
    TODO: Hacer un procedimiento almacenado para eliminar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        articulos = FXCollections.observableArrayList();
        radioNuevo.setToggleGroup(toggleGroup);
        radioNuevo.setSelected(true);
        radioModificar.setToggleGroup(toggleGroup);
        radioEliminar.setToggleGroup(toggleGroup);

        tablaDatos.setPlaceholder(new Label("Sin articulos que mostrar"));
        tablaDatos.setItems(articulos);

        columnClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnFamilia.setCellValueFactory(new PropertyValueFactory<>("famId"));

        txtNombre.requestFocus();
        llenarCombo();
        llenarTabla();
    }

    @FXML
    public void grabar() {
        labelMessages.setVisible(false);
        if (!validarPrecio()) {
            txtPrecio.requestFocus();
            return;
        }

        if (!validarDatos()) {
            labelMessages.setVisible( true );
            labelMessages.setText( msg );
            return;
        }

        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        String idFam = cmbFamilias.getValue().substring(0, cmbFamilias.getValue().indexOf(" "));
        int famId = Integer.parseInt(idFam);

        Articulo articulo = new Articulo(nombre, descripcion, precio, famId);

        if (!insertarDatos(articulo)) {
            labelMessages.setVisible( true );
            labelMessages.setText(msg);
            return;
        }

        llenarTabla();

        labelMessages.setText("DATOS GRABADOS EXITOSAMENTE");
        labelMessages.setVisible(true);
    }

    @FXML
    public void limpiar() {

        deshabilitarCampos( false );
        if (labelMessages.isVisible()) {
            labelMessages.setVisible(false);
        }

        clear();
        txtClave.setText("");
        txtClave.setDisable(true);
        radioNuevo.setSelected(true);
    }

    @FXML
    public void cambiar() {

        deshabilitarCampos( false );

        if (radioNuevo.isSelected()) {
            txtClave.setDisable(true);
            txtClave.setText("");
            limpiar();
            return;
        }

        if (radioModificar.isSelected()) {
            txtClave.setDisable(false);
            txtClave.setText("");
            clear();
        }

        if (radioEliminar.isSelected()) {
            txtClave.setDisable(false);
            txtClave.setText("");
            deshabilitarCampos( true );
            clear();
        }

    }

    @FXML
    public void consultarDatos() {

        labelMessages.setVisible(false);

        try {

            int clave = Integer.parseInt(txtClave.getText());

            String query = "SELECT * FROM vw_familias WHERE artid = " + clave;
            Statement smt = MainController.coneccion.createStatement();
            ResultSet tupla = smt.executeQuery(query);

            if (!tupla.next()) {
                msg = "ESTE ID NO EXISTE";
                labelMessages.setText(msg);
                labelMessages.setVisible(true);
                clear();
                return;
            }

            String nombre = tupla.getString("artnombre");
            String descripcion = tupla.getString("artdescripcion");
            double precio = tupla.getDouble("artprecio");
            int famid = tupla.getInt("famid");

            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);
            txtPrecio.setText(precio + "");
            cmbFamilias.getSelectionModel().select(famid - 1);

        } catch (SQLException error) {
            msg = error.getMessage();
        } catch (Exception error) {
            clear();
            txtClave.requestFocus();
        }
    }

    private void clear() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        cmbFamilias.getSelectionModel().clearSelection();
    }


    private boolean validarPrecio() {

        try {

            Double.parseDouble(txtPrecio.getText());

        } catch (Exception error) {
            labelMessages.setText("El precio debe de ser numerico");
            labelMessages.setVisible(true);
            System.out.println(error.getMessage());
            return false;
        }

        return true;
    }

    private boolean validarDatos() {
        if (txtNombre.getText().isEmpty()) {
            msg = "Faltan datos por ingresar";
            return false;
        }

        if (txtDescripcion.getText().isEmpty()) {
            msg = "Faltan datos por ingresar";
            return false;
        }

        if (cmbFamilias.getSelectionModel().isEmpty()) {
            msg = "Faltan datos por ingresar";
            return false;
        }

        return true;


    }

    private void llenarCombo() {

        String query = "select * from familias";
        cmbFamilias.getItems().clear();

        try {
            MainController.hacerConexion();
            Statement smt = MainController.coneccion.createStatement();
            ResultSet tuplas = smt.executeQuery(query);

            while (tuplas.next()) {
                String familia = tuplas.getInt(("famid")) + " - " + tuplas.getString("famnombre");
                cmbFamilias.getItems().add(familia);
            }

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

    }

    private void llenarTabla() {

        articulos.clear();
        try {
            Articulo articulo;
            Statement smt = MainController.coneccion.createStatement();
            ResultSet tuplas = smt.executeQuery("select * from articulos");
            while (tuplas.next()) {
                articulo = new Articulo(tuplas.getInt("artid"),
                        tuplas.getString("artnombre"),
                        tuplas.getString("artdescripcion"),
                        tuplas.getDouble("artprecio"),
                        tuplas.getInt("famid"));
                articulos.add(articulo);
            }
            tablaDatos.refresh();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private boolean insertarDatos(Articulo articulo) {

        int opcion = saberOpcion();

        try {

            int clave = 0;
            if (!txtClave.getText().isEmpty() && opcion != 0) {
                clave = Integer.parseInt(txtClave.getText());
            }

            String nombre = articulo.getNombre();
            String descripcion = articulo.getDescripcion();
            double precio = articulo.getPrecio();
            int famId = articulo.getFamId();
            System.out.println( clave );
            CallableStatement callableStatement = MainController.coneccion.prepareCall(
                    "{call sp_MttoArticulos (?,?,?,?,?,?)}"
            );

            callableStatement.registerOutParameter("artid", Types.INTEGER);
            callableStatement.setInt("artid", clave);
            callableStatement.setString("artNombre", nombre);
            callableStatement.setString("artDescripcion", descripcion);
            callableStatement.setDouble("artPrecio", precio);
            callableStatement.setInt("famId", famId);
            callableStatement.setInt("opcion", opcion);

            callableStatement.execute();
            txtClave.setText(callableStatement.getInt("artid") + "");
            articulo.setClave(callableStatement.getInt("artid"));

        } catch (Exception error) {
            msg = error.getMessage();
            System.out.println(error.getMessage());
            return false;
        }

        return true;
    }

    private int saberOpcion() {

        if (radioNuevo.isSelected()) {
            return 0;
        }

        if (radioModificar.isSelected()) {
            return 1;
        }

        return 2;
    }

    private void deshabilitarCampos(boolean estado) {
        txtNombre.setDisable( estado );
        txtDescripcion.setDisable( estado );
        txtPrecio.setDisable( estado );
        cmbFamilias.setDisable( estado );
    }

}
