package com.example.esparzaproyecto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {

    @FXML
    RadioButton radioNuevo, radioModificar, radioEliminar;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioNuevo.setToggleGroup( toggleGroup );
        radioNuevo.setSelected( true );
        radioModificar.setToggleGroup( toggleGroup );
        radioEliminar.setToggleGroup( toggleGroup );
    }


}
