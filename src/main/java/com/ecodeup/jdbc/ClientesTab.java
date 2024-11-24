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

public class ClientesTab {

    public static BorderPane getClientesPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Cliente> table = new TableView<>();
        TableColumn<Cliente, String> colId = new TableColumn<>("ID");
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Cliente, String> colCedula = new TableColumn<>("Cédula");
        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        TableColumn<Cliente, String> colCorreo = new TableColumn<>("Correo");

        // Configurar columnas
        colId.setCellValueFactory(data -> data.getValue().idProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colCedula.setCellValueFactory(data -> data.getValue().cedulaProperty());
        colTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());

        table.getColumns().addAll(colId, colNombre, colCedula, colTelefono, colCorreo);

        // Botones
        Button btnCargar = new Button("Cargar Clientes");
        Button btnAgregar = new Button("Agregar Cliente");
        Button btnEditar = new Button("Editar Cliente");
        Button btnEliminar = new Button("Eliminar Cliente");

        btnCargar.setOnAction(e -> cargarClientes(table));
        btnAgregar.setOnAction(e -> agregarCliente(table));
        btnEditar.setOnAction(e -> editarCliente(table));
        btnEliminar.setOnAction(e -> eliminarCliente(table));

        // Layout para botones
        HBox buttonBox = new HBox(10, btnCargar, btnAgregar, btnEditar, btnEliminar);
        VBox mainLayout = new VBox(10, table, buttonBox);

        pane.setCenter(mainLayout);

        return pane;
    }

    private static void cargarClientes(TableView<Cliente> table) {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM Clientes";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Cliente cliente = new Cliente(
                            resultSet.getString("id_cliente"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cedula"),
                            resultSet.getString("telefono"),
                            resultSet.getString("correo")
                    );
                    clientes.add(cliente);
                }

                table.setItems(clientes);

                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void agregarCliente(TableView<Cliente> table) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Agregar Cliente");

        TextField txtNombre = new TextField();
        TextField txtCedula = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtCorreo = new TextField();

        txtNombre.setPromptText("Nombre");
        txtCedula.setPromptText("Cédula");
        txtTelefono.setPromptText("Teléfono");
        txtCorreo.setPromptText("Correo");

        VBox content = new VBox(10, txtNombre, txtCedula, txtTelefono, txtCorreo);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Cliente(null, txtNombre.getText(), txtCedula.getText(),
                        txtTelefono.getText(), txtCorreo.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(cliente -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO Clientes (nombre, cedula, telefono, correo) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, cliente.getNombre());
                    statement.setString(2, cliente.getCedula());
                    statement.setString(3, cliente.getTelefono());
                    statement.setString(4, cliente.getCorreo());
                    statement.executeUpdate();
                    cargarClientes(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void editarCliente(TableView<Cliente> table) {
        Cliente clienteSeleccionado = table.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un cliente para editar.");
            alert.show();
            return;
        }

        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Editar Cliente");

        TextField txtNombre = new TextField(clienteSeleccionado.getNombre());
        TextField txtCedula = new TextField(clienteSeleccionado.getCedula());
        TextField txtTelefono = new TextField(clienteSeleccionado.getTelefono());
        TextField txtCorreo = new TextField(clienteSeleccionado.getCorreo());

        VBox content = new VBox(10, txtNombre, txtCedula, txtTelefono, txtCorreo);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Cliente(clienteSeleccionado.getId(), txtNombre.getText(), txtCedula.getText(),
                        txtTelefono.getText(), txtCorreo.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(cliente -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "UPDATE Clientes SET nombre = ?, cedula = ?, telefono = ?, correo = ? WHERE id_cliente = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, cliente.getNombre());
                    statement.setString(2, cliente.getCedula());
                    statement.setString(3, cliente.getTelefono());
                    statement.setString(4, cliente.getCorreo());
                    statement.setString(5, cliente.getId());
                    statement.executeUpdate();
                    cargarClientes(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void eliminarCliente(TableView<Cliente> table) {
        Cliente clienteSeleccionado = table.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un cliente para eliminar.");
            alert.show();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar el cliente?");
        confirmacion.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection != null) {
                        String query = "DELETE FROM Clientes WHERE id_cliente = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, clienteSeleccionado.getId());
                        statement.executeUpdate();
                        cargarClientes(table);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
