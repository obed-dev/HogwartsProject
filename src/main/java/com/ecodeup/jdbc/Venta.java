package com.ecodeup.jdbc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Venta {
    private final StringProperty idVenta;
    private final StringProperty producto;
    private final StringProperty cliente;
    private final StringProperty fechaHora;
    private final StringProperty metodoPago;
    private final StringProperty empleado;


    public Venta(String idVenta, String producto, String cliente ,String fechaHora, String metodoPago , String empleado) {
        this.idVenta = new SimpleStringProperty(idVenta);
        this.producto = new SimpleStringProperty(producto);
        this.cliente = new SimpleStringProperty(cliente);
        this.fechaHora = new SimpleStringProperty(fechaHora);
        this.metodoPago = new SimpleStringProperty(metodoPago);
        this.empleado = new SimpleStringProperty(empleado);

    }

    public StringProperty idVentaProperty() {
        return idVenta;
    }

    public StringProperty fechaHoraProperty() {
        return fechaHora;
    }

    public StringProperty metodoPagoProperty() {
        return metodoPago;
    }

    public StringProperty productoProperty() {
        return producto;
    }

    public StringProperty clienteProperty() {
        return cliente;
    }

    public StringProperty empleadoProperty() {
        return empleado;
    }

    public String getIdVenta() {
        return idVenta.get();
    }

    public String getProducto() {
        return producto.get();
    }

    public String getCliente() {
        return cliente.get();
    }

    public String getFechaHora() {
        return fechaHora.get();
    }

    public String getMetodoPago() {
        return metodoPago.get();
    }
    public String getEmpleado() {
        return empleado.get();
    }

}
