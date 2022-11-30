package com.example.esparzaproyecto.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Familia {

    private SimpleStringProperty familia;
    private SimpleStringProperty articulo, descripcion;
    private SimpleIntegerProperty artId, famId;
    private SimpleDoubleProperty precio;

    public Familia( int famID, String familia, int artId, String articulo, String descripcion, double precio) {
        this.famId = new SimpleIntegerProperty(famID);
        this.familia = new SimpleStringProperty(familia);
        this.artId = new SimpleIntegerProperty(artId);
        this.articulo = new SimpleStringProperty(articulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleDoubleProperty(precio);
    }

    public String getFamilia() {
        return familia.get();
    }

    public SimpleStringProperty familiaProperty() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia.set(familia);
    }

    public String getArticulo() {
        return articulo.get();
    }

    public SimpleStringProperty articuloProperty() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo.set(articulo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public int getArtId() {
        return artId.get();
    }

    public SimpleIntegerProperty artIdProperty() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId.set(artId);
    }

    public int getFamId() {
        return famId.get();
    }

    public SimpleIntegerProperty famIdProperty() {
        return famId;
    }

    public void setFamId(int famId) {
        this.famId.set(famId);
    }

    public double getPrecio() {
        return precio.get();
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }
}
