package com.example.esparzaproyecto.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Articulo {

    private SimpleStringProperty nombre, descripcion;
    private SimpleIntegerProperty clave, famId;
    private SimpleDoubleProperty precio;

    public Articulo(int clave, String nombre, String descripcion, double precio, int famID) {
        this.clave = new SimpleIntegerProperty(clave);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleDoubleProperty(precio);
        this.famId = new SimpleIntegerProperty(famID);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public int getClave() {
        return clave.get();
    }

    public SimpleIntegerProperty claveProperty() {
        return clave;
    }

    public double getPrecio() {
        return precio.get();
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    public int getFamId() {
        return famId.get();
    }

    public SimpleIntegerProperty famIdProperty() {
        return famId;
    }
}
