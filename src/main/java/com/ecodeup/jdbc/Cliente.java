package com.ecodeup.jdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty cedula;
    private final StringProperty telefono;
    private final StringProperty correo;

    public Cliente(String id, String nombre, String cedula, String telefono, String correo) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cedula = new SimpleStringProperty(cedula);
        this.telefono = new SimpleStringProperty(telefono);
        this.correo = new SimpleStringProperty(correo);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty cedulaProperty() {
        return cedula;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public StringProperty correoProperty() {
        return correo;
    }

    public String getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getCedula() {
        return cedula.get();
    }

    public String getTelefono() {
        return telefono.get();
    }

    public String getCorreo() {
        return correo.get();
    }
}
