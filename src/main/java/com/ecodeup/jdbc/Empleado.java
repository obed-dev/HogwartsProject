package com.ecodeup.jdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty rol;
    private final StringProperty horario;
    private final StringProperty salario;
    private final StringProperty telefono;

    public Empleado(String id, String nombre, String rol, String horario, String salario, String telefono) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.rol = new SimpleStringProperty(rol);
        this.horario = new SimpleStringProperty(horario);
        this.salario = new SimpleStringProperty(salario);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty rolProperty() {
        return rol;
    }

    public StringProperty horarioProperty() {
        return horario;
    }

    public StringProperty salarioProperty() {
        return salario;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getRol() {
        return rol.get();
    }

    public String getHorario() {
        return horario.get();
    }

    public String getSalario() {
        return salario.get();
    }

    public String getTelefono() {
        return telefono.get();
    }
}
