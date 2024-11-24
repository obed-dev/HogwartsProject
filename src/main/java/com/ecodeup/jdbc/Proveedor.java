package com.ecodeup.jdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proveedor {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty contacto;
    private final StringProperty productos;
    private final StringProperty fechaEntrega;

    public Proveedor(String id, String nombre, String contacto, String productos, String fechaEntrega) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.contacto = new SimpleStringProperty(contacto);
        this.productos = new SimpleStringProperty(productos);
        this.fechaEntrega = new SimpleStringProperty(fechaEntrega);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty contactoProperty() {
        return contacto;
    }

    public StringProperty productosProperty() {
        return productos;
    }

    public StringProperty fechaEntregaProperty() {
        return fechaEntrega;
    }

    public String getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getContacto() {
        return contacto.get();
    }

    public String getProductos() {
        return productos.get();
    }

    public String getFechaEntrega() {
        return fechaEntrega.get();
    }
}
