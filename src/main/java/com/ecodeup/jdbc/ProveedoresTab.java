package com.ecodeup.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProveedoresTab {

    public static BorderPane getProveedoresPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Proveedor> table = new TableView<>();
        TableColumn<Proveedor, String> colId = new TableColumn<>("ID");
        TableColumn<Proveedor, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Proveedor, String> colContacto = new TableColumn<>("Contacto");
        TableColumn<Proveedor, String> colProductos = new TableColumn<>("Productos Suministrados");
        TableColumn<Proveedor, String> colFechaEntrega = new TableColumn<>("Fecha de Entrega");

        // Configurar columnas
        colId.setCellValueFactory(data -> data.getValue().idProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colContacto.setCellValueFactory(data -> data.getValue().contactoProperty());
        colProductos.setCellValueFactory(data -> data.getValue().productosProperty());
        colFechaEntrega.setCellValueFactory(data -> data.getValue().fechaEntregaProperty());

        table.getColumns().addAll(colId, colNombre, colContacto, colProductos, colFechaEntrega);

        // Botones
        Button btnCargar = new Button("Cargar Proveedores");
        Button btnAgregar = new Button("Agregar Proveedor");
        Button btnEditar = new Button("Editar Proveedor");
        Button btnEliminar = new Button("Eliminar Proveedor");

        btnCargar.setOnAction(e -> cargarProveedores(table));
        btnAgregar.setOnAction(e -> agregarProveedor(table));
        btnEditar.setOnAction(e -> editarProveedor(table));
        btnEliminar.setOnAction(e -> eliminarProveedor(table));

        // Layout para botones
        HBox buttonBox = new HBox(10, btnCargar, btnAgregar, btnEditar, btnEliminar);
        VBox mainLayout = new VBox(10, table, buttonBox);

        pane.setCenter(mainLayout);

        return pane;
    }

    private static void cargarProveedores(TableView<Proveedor> table) {
        ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM Proveedores";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Proveedor proveedor = new Proveedor(
                            resultSet.getString("id_proveedor"),
                            resultSet.getString("nombre"),
                            resultSet.getString("contacto"),
                            resultSet.getString("productos_suministrados"),
                            resultSet.getString("fecha_entrega")
                    );
                    proveedores.add(proveedor);
                }

                table.setItems(proveedores);

                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void agregarProveedor(TableView<Proveedor> table) {
        Dialog<Proveedor> dialog = new Dialog<>();
        dialog.setTitle("Agregar Proveedor");

        TextField txtNombre = new TextField();
        TextField txtContacto = new TextField();
        TextField txtProductos = new TextField();
        TextField txtFechaEntrega = new TextField();

        txtNombre.setPromptText("Nombre");
        txtContacto.setPromptText("Contacto");
        txtProductos.setPromptText("Productos Suministrados");
        txtFechaEntrega.setPromptText("Fecha de Entrega");

        VBox content = new VBox(10, txtNombre, txtContacto, txtProductos, txtFechaEntrega);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Proveedor(null, txtNombre.getText(), txtContacto.getText(),
                        txtProductos.getText(), txtFechaEntrega.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(proveedor -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO Proveedores (nombre, contacto, productos_suministrados, fecha_entrega) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, proveedor.getNombre());
                    statement.setString(2, proveedor.getContacto());
                    statement.setString(3, proveedor.getProductos());
                    statement.setString(4, proveedor.getFechaEntrega());
                    statement.executeUpdate();
                    cargarProveedores(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void editarProveedor(TableView<Proveedor> table) {
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un proveedor para editar.");
            alert.show();
            return;
        }

        Dialog<Proveedor> dialog = new Dialog<>();
        dialog.setTitle("Editar Proveedor");

        TextField txtNombre = new TextField(proveedorSeleccionado.getNombre());
        TextField txtContacto = new TextField(proveedorSeleccionado.getContacto());
        TextField txtProductos = new TextField(proveedorSeleccionado.getProductos());
        TextField txtFechaEntrega = new TextField(proveedorSeleccionado.getFechaEntrega());

        VBox content = new VBox(10, txtNombre, txtContacto, txtProductos, txtFechaEntrega);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Proveedor(proveedorSeleccionado.getId(), txtNombre.getText(), txtContacto.getText(),
                        txtProductos.getText(), txtFechaEntrega.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(proveedor -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "UPDATE Proveedores SET nombre = ?, contacto = ?, productos_suministrados = ?, fecha_entrega = ? WHERE id_proveedor = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, proveedor.getNombre());
                    statement.setString(2, proveedor.getContacto());
                    statement.setString(3, proveedor.getProductos());
                    statement.setString(4, proveedor.getFechaEntrega());
                    statement.setString(5, proveedor.getId());
                    statement.executeUpdate();
                    cargarProveedores(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void eliminarProveedor(TableView<Proveedor> table) {
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un proveedor para eliminar.");
            alert.show();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar el proveedor?");
        confirmacion.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection != null) {
                        String query = "DELETE FROM Proveedores WHERE id_proveedor = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, proveedorSeleccionado.getId());
                        statement.executeUpdate();
                        cargarProveedores(table);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
