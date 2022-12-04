package com.example.esparzaproyecto.controllers;

import com.example.esparzaproyecto.models.Articulo;
import com.example.esparzaproyecto.models.Conexion;
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
    private RadioButton radioNuevo, radioModificar, radioEliminar;

    @FXML
    private ComboBox<String> cmbFamilias;

    @FXML
    private TableView<Articulo> tablaDatos;

    @FXML
    private TableColumn<String, Articulo> columnClave, columnNombre, columnDescripcion, columnPrecio, columnFamilia;

    @FXML
    private TextField txtClave, txtNombre, txtDescripcion, txtPrecio;

    @FXML
    private ObservableList<Articulo> articulos;

    @FXML
    private Label labelMessages, lblNombre, lblPrecio, lblDesc, lblCombo, lblClave;

    private String msg;

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
        lblNombre.setVisible(false);
        lblDesc.setVisible(false);
        lblPrecio.setVisible(false);
        lblCombo.setVisible(false);

        if (!validarDatos()) {
            return;
        }

        if (!validarPrecio()) {
            txtPrecio.requestFocus();
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
        clear();
    }

    @FXML
    public void limpiar() {

        deshabilitarCampos( false );
        if (labelMessages.isVisible()) {
            labelMessages.setVisible(false);
        }

        clear();
        limpiarLabels();
        txtClave.setText("");
        txtClave.setDisable(true);
        radioNuevo.setSelected(true);
    }

    @FXML
    public void cambiar() {

        deshabilitarCampos( false );
        limpiarLabels();

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

            String query = "SELECT * FROM articulos WHERE artid = " + clave;
            Statement smt = Conexion.getConexion().createStatement();
            ResultSet tupla = smt.executeQuery(query);

            if (!tupla.next()) {
                msg = "ESTE ID NO EXISTE";
                lblClave.setText(msg);
                lblClave.setVisible(true);
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

    @FXML
    public void limpiarPantalla() {
        limpiarLabels();
    }

    private void clear() {
        txtClave.setText("");
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
        int errores = 0;
        if (txtNombre.getText().isEmpty()) {
            lblNombre.setVisible( true );
            errores++;
        }

        if (txtDescripcion.getText().isEmpty()) {
            lblDesc.setVisible( true );
            errores++;
        }

        if (txtPrecio.getText().isEmpty()) {
            lblPrecio.setVisible( true );
            errores++;
        }

        if (cmbFamilias.getSelectionModel().isEmpty()) {
            lblCombo.setVisible( true );
            errores++;
        }


        return errores == 0;


    }

    private void llenarCombo() {

        String query = "select * from familias";
        cmbFamilias.getItems().clear();

        try {

            Statement smt = Conexion.getConexion().createStatement();
            ResultSet tuplas = smt.executeQuery(query);

            while (tuplas.next()) {
                String familia = tuplas.getInt(("famid")) + " - " + tuplas.getString("famnombre");
                cmbFamilias.getItems().add(familia);
            }

        } catch (SQLException error) {

        }

    }

    private void llenarTabla() {

        articulos.clear();
        try {
            Articulo articulo;
            Statement smt = Conexion.getConexion().createStatement();
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
        } catch (SQLException error) {
            System.out.println(error.getSQLState());
        }
    }

    private boolean insertarDatos(Articulo articulo) {


        String procedimiento = saberProcedimiento();

        try {

            int clave = 0;
            if (!txtClave.getText().isEmpty()) {
                clave = Integer.parseInt(txtClave.getText());
            }

            String nombre = articulo.getNombre();
            String descripcion = articulo.getDescripcion();
            double precio = articulo.getPrecio();
            int famId = articulo.getFamId();

            CallableStatement callableStatement = Conexion.getConexion().prepareCall(procedimiento);

            callableStatement.setInt(1, clave);
            if (procedimiento.equals("{call sp_MttoArticulos (?,?,?,?,?)}")) {
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.setString(2, nombre);
                callableStatement.setString(3, descripcion);
                callableStatement.setDouble(4, precio);
                callableStatement.setInt(5, famId);
            }

            callableStatement.execute();

            if (procedimiento.equals("{call sp_MttoArticulos (?,?,?,?,?)}")) {
                txtClave.setText(callableStatement.getInt("artid") + "");
                articulo.setClave(callableStatement.getInt("artid"));
            }

        } catch (SQLException error) {
            msg = evaluarError(error.getMessage());
            System.out.println(error.getMessage());
            return false;
        }

        return true;
    }

    private String saberProcedimiento() {

        if (radioNuevo.isSelected() || radioModificar.isSelected()) {
            return "{call sp_MttoArticulos (?,?,?,?,?)}";
        }

        return "{call sp_mttoArticulosEliminar (?)}";
    }

    private void deshabilitarCampos(boolean estado) {
        txtNombre.setDisable( estado );
        txtDescripcion.setDisable( estado );
        txtPrecio.setDisable( estado );
        cmbFamilias.setDisable( estado );
    }

    private void limpiarLabels() {
        labelMessages.setVisible(false);
        lblNombre.setVisible(false);
        lblDesc.setVisible(false);
        lblPrecio.setVisible(false);
        lblCombo.setVisible(false);
    }

    private String evaluarError(String errorMessage) {

        if (errorMessage.contains("Could not find")) {
            return "El proc. almacenado no existe, favor de crearlo";
        }
        return errorMessage;

    }

}
