package com.ecodeup.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventariosTab {

    public static BorderPane getInventariosPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Inventario> table = new TableView<>();

        // Columnas de la tabla
        TableColumn<Inventario, String> colIdInventario = new TableColumn<>("ID Inventario");
        TableColumn<Inventario, String> colProducto = new TableColumn<>("Producto");
        TableColumn<Inventario, Integer> colCantidadVendida = new TableColumn<>("Cantidad Vendida");
        TableColumn<Inventario, Integer> colExistencias = new TableColumn<>("Existencias Iniciales");
        TableColumn<Inventario, Integer> colExistenciasActuales = new TableColumn<>("Existencias Actuales");
        TableColumn<Inventario, String> colFechaReposicion = new TableColumn<>("Fecha Reposición");

        // Configurar columnas
        colIdInventario.setCellValueFactory(data -> data.getValue().idInventarioProperty());
        colProducto.setCellValueFactory(data -> data.getValue().productoProperty());
        colCantidadVendida.setCellValueFactory(data -> data.getValue().cantidadVendidaProperty().asObject());
        colExistencias.setCellValueFactory(data -> data.getValue().existenciasProperty().asObject());
        colExistenciasActuales.setCellValueFactory(data -> data.getValue().existenciasActualesProperty().asObject());
        colFechaReposicion.setCellValueFactory(data -> data.getValue().fechaReposicionProperty());

        table.getColumns().addAll(colIdInventario, colProducto, colCantidadVendida, colExistencias, colExistenciasActuales, colFechaReposicion);

        // Botón para cargar inventario
        Button btnCargar = new Button("Cargar Inventario");
        btnCargar.setOnAction(e -> cargarInventario(table));

        HBox buttonBox = new HBox(10, btnCargar);
        pane.setTop(buttonBox);
        pane.setCenter(table);

        return pane;
    }

    private static void cargarInventario(TableView<Inventario> table) {
        ObservableList<Inventario> inventarios = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // Consulta SQL con JOIN para obtener el inventario actualizado
                String query = "SELECT i.id_inventario, p.nombre AS producto, i.cantidad_vendida, " +
                        "p.existencias, i.existencias_actuales, i.fecha_reposicion " +
                        "FROM inventarios i " +
                        "INNER JOIN productos p ON i.id_producto = p.id_producto";

                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Inventario inventario = new Inventario(
                            resultSet.getString("id_inventario"),
                            resultSet.getString("producto"),
                            resultSet.getInt("cantidad_vendida"),
                            resultSet.getInt("existencias"),
                            resultSet.getInt("existencias_actuales"),
                            resultSet.getString("fecha_reposicion")
                    );
                    inventarios.add(inventario);
                }

                table.setItems(inventarios);
                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
