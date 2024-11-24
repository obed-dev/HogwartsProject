package com.ecodeup.jdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Producto {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final StringProperty precio;
    private final StringProperty categoria;
    private final StringProperty existencias;

    public Producto(String id, String nombre, String descripcion, String precio, String categoria, String existencias) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleStringProperty(precio);
        this.categoria = new SimpleStringProperty(categoria);
        this.existencias = new SimpleStringProperty(existencias);
    }

    // Métodos getter para propiedades (usados en la tabla)
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public StringProperty precioProperty() {
        return precio;
    }

    public StringProperty categoriaProperty() {
        return categoria;
    }

    public StringProperty existenciasProperty() {
        return existencias;
    }

    // Métodos getter tradicionales (usados en otras partes del código)
    public String getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getPrecio() {
        return precio.get();
    }

    public String getCategoria() {
        return categoria.get();
    }

    public String getExistencias() {
        return existencias.get();
    }
}
