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

public class ProductosTab {

    public static BorderPane getProductosPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Producto> table = new TableView<>();
        TableColumn<Producto, String> colId = new TableColumn<>("ID");
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, String> colDescripcion = new TableColumn<>("Descripción");
        TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        TableColumn<Producto, String> colExistencias = new TableColumn<>("Existencias");

        // Configurar columnas
        colId.setCellValueFactory(data -> data.getValue().idProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colDescripcion.setCellValueFactory(data -> data.getValue().descripcionProperty());
        colPrecio.setCellValueFactory(data -> data.getValue().precioProperty());
        colCategoria.setCellValueFactory(data -> data.getValue().categoriaProperty());
        colExistencias.setCellValueFactory(data -> data.getValue().existenciasProperty());

        table.getColumns().addAll(colId, colNombre, colDescripcion, colPrecio, colCategoria, colExistencias);

        // Botones para acciones
        Button btnCargar = new Button("Cargar Productos");
        Button btnAgregar = new Button("Agregar Producto");
        Button btnEditar = new Button("Editar Producto");
        Button btnEliminar = new Button("Eliminar Producto");

        // Configuración de acciones
        btnCargar.setOnAction(e -> cargarProductos(table));

        btnAgregar.setOnAction(e -> agregarProducto(table));

        btnEditar.setOnAction(e -> editarProducto(table));

        btnEliminar.setOnAction(e -> eliminarProducto(table));

        // Layout para botones
        HBox buttonBox = new HBox(10, btnCargar, btnAgregar, btnEditar, btnEliminar);

        // Layout principal
        VBox mainLayout = new VBox(10, table, buttonBox);
        pane.setCenter(mainLayout);

        return pane;
    }

    // Función para cargar productos desde la base de datos
    private static void cargarProductos(TableView<Producto> table) {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT id_producto, nombre, descripcion, precio, categoria, existencias FROM Productos";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Producto producto = new Producto(
                            resultSet.getString("id_producto"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("precio"),
                            resultSet.getString("categoria"),
                            resultSet.getString("existencias")
                    );
                    productos.add(producto);
                }

                table.setItems(productos);

                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Función para agregar un producto
    private static void agregarProducto(TableView<Producto> table) {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Agregar Producto");

        // Crear campos de entrada
        TextField txtNombre = new TextField();
        TextField txtDescripcion = new TextField();
        TextField txtPrecio = new TextField();
        TextField txtCategoria = new TextField();
        TextField txtExistencias = new TextField();

        txtNombre.setPromptText("Nombre");
        txtDescripcion.setPromptText("Descripción");
        txtPrecio.setPromptText("Precio");
        txtCategoria.setPromptText("Categoría");
        txtExistencias.setPromptText("Existencias");

        VBox content = new VBox(10, txtNombre, txtDescripcion, txtPrecio, txtCategoria, txtExistencias);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Producto(null, txtNombre.getText(), txtDescripcion.getText(),
                        txtPrecio.getText(), txtCategoria.getText(), txtExistencias.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(producto -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO Productos (nombre, descripcion, precio, categoria, existencias) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, producto.getNombre());
                    statement.setString(2, producto.getDescripcion());
                    statement.setString(3, producto.getPrecio());
                    statement.setString(4, producto.getCategoria());
                    statement.setString(5, producto.getExistencias());
                    statement.executeUpdate();
                    cargarProductos(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Función para editar un producto
    private static void editarProducto(TableView<Producto> table) {
        Producto productoSeleccionado = table.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un producto para editar.");
            alert.show();
            return;
        }

        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Editar Producto");

        // Crear campos de entrada
        TextField txtNombre = new TextField(productoSeleccionado.getNombre());
        TextField txtDescripcion = new TextField(productoSeleccionado.getDescripcion());
        TextField txtPrecio = new TextField(productoSeleccionado.getPrecio());
        TextField txtCategoria = new TextField(productoSeleccionado.getCategoria());
        TextField txtExistencias = new TextField(productoSeleccionado.getExistencias());

        VBox content = new VBox(10, txtNombre, txtDescripcion, txtPrecio, txtCategoria, txtExistencias);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Producto(productoSeleccionado.getId(), txtNombre.getText(), txtDescripcion.getText(),
                        txtPrecio.getText(), txtCategoria.getText(), txtExistencias.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(producto -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, existencias = ? WHERE id_producto = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, producto.getNombre());
                    statement.setString(2, producto.getDescripcion());
                    statement.setString(3, producto.getPrecio());
                    statement.setString(4, producto.getCategoria());
                    statement.setString(5, producto.getExistencias());
                    statement.setString(6, producto.getId());
                    statement.executeUpdate();
                    cargarProductos(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Función para eliminar un producto
    private static void eliminarProducto(TableView<Producto> table) {
        Producto productoSeleccionado = table.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un producto para eliminar.");
            alert.show();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar el producto?");
        confirmacion.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection != null) {
                        String query = "DELETE FROM Productos WHERE id_producto = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, productoSeleccionado.getId());
                        statement.executeUpdate();
                        cargarProductos(table);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
