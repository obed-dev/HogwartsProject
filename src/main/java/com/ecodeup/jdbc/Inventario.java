package com.ecodeup.jdbc;

import javafx.beans.property.*;

public class Inventario {
    private final StringProperty idInventario;
    private final StringProperty producto;
    private final IntegerProperty cantidadVendida;
    private final IntegerProperty existencias;
    private final IntegerProperty existenciasActuales;
    private final StringProperty fechaReposicion;

    public Inventario(String idInventario, String producto, int cantidadVendida, int existencias, int existenciasActuales, String fechaReposicion) {
        this.idInventario = new SimpleStringProperty(idInventario);
        this.producto = new SimpleStringProperty(producto);
        this.cantidadVendida = new SimpleIntegerProperty(cantidadVendida);
        this.existencias = new SimpleIntegerProperty(existencias);
        this.existenciasActuales = new SimpleIntegerProperty(existenciasActuales);
        this.fechaReposicion = new SimpleStringProperty(fechaReposicion);
    }

    public StringProperty idInventarioProperty() {
        return idInventario;
    }

    public StringProperty productoProperty() {
        return producto;
    }

    public IntegerProperty cantidadVendidaProperty() {
        return cantidadVendida;
    }

    public IntegerProperty existenciasProperty() {
        return existencias;
    }

    public IntegerProperty existenciasActualesProperty() {
        return existenciasActuales;
    }

    public StringProperty fechaReposicionProperty() {
        return fechaReposicion;
    }
}
