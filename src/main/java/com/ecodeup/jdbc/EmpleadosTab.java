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

public class EmpleadosTab {

    public static BorderPane getEmpleadosPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Empleado> table = new TableView<>();
        TableColumn<Empleado, String> colId = new TableColumn<>("ID");
        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Empleado, String> colRol = new TableColumn<>("Rol");
        TableColumn<Empleado, String> colHorario = new TableColumn<>("Horario");
        TableColumn<Empleado, String> colSalario = new TableColumn<>("Salario");
        TableColumn<Empleado, String> colTelefono = new TableColumn<>("Teléfono");

        // Configurar columnas
        colId.setCellValueFactory(data -> data.getValue().idProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colRol.setCellValueFactory(data -> data.getValue().rolProperty());
        colHorario.setCellValueFactory(data -> data.getValue().horarioProperty());
        colSalario.setCellValueFactory(data -> data.getValue().salarioProperty());
        colTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());

        table.getColumns().addAll(colId, colNombre, colRol, colHorario, colSalario, colTelefono);

        // Botones
        Button btnCargar = new Button("Cargar Empleados");
        Button btnAgregar = new Button("Agregar Empleado");
        Button btnEditar = new Button("Editar Empleado");
        Button btnEliminar = new Button("Eliminar Empleado");

        btnCargar.setOnAction(e -> cargarEmpleados(table));
        btnAgregar.setOnAction(e -> agregarEmpleado(table));
        btnEditar.setOnAction(e -> editarEmpleado(table));
        btnEliminar.setOnAction(e -> eliminarEmpleado(table));

        // Layout para botones
        HBox buttonBox = new HBox(10, btnCargar, btnAgregar, btnEditar, btnEliminar);
        VBox mainLayout = new VBox(10, table, buttonBox);

        pane.setCenter(mainLayout);

        return pane;
    }

    private static void cargarEmpleados(TableView<Empleado> table) {
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM Empleados";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Empleado empleado = new Empleado(
                            resultSet.getString("id_empleado"),
                            resultSet.getString("nombre"),
                            resultSet.getString("rol"),
                            resultSet.getString("horario"),
                            resultSet.getString("salario"),
                            resultSet.getString("telefono")
                    );
                    empleados.add(empleado);
                }

                table.setItems(empleados);

                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void agregarEmpleado(TableView<Empleado> table) {
        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle("Agregar Empleado");

        TextField txtNombre = new TextField();
        TextField txtRol = new TextField();
        TextField txtHorario = new TextField();
        TextField txtSalario = new TextField();
        TextField txtTelefono = new TextField();

        txtNombre.setPromptText("Nombre");
        txtRol.setPromptText("Rol");
        txtHorario.setPromptText("Horario");
        txtSalario.setPromptText("Salario");
        txtTelefono.setPromptText("Teléfono");

        VBox content = new VBox(10, txtNombre, txtRol, txtHorario, txtSalario, txtTelefono);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Empleado(null, txtNombre.getText(), txtRol.getText(),
                        txtHorario.getText(), txtSalario.getText(), txtTelefono.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(empleado -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO Empleados (nombre, rol, horario, salario, telefono) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, empleado.getNombre());
                    statement.setString(2, empleado.getRol());
                    statement.setString(3, empleado.getHorario());
                    statement.setString(4, empleado.getSalario());
                    statement.setString(5, empleado.getTelefono());
                    statement.executeUpdate();
                    cargarEmpleados(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void editarEmpleado(TableView<Empleado> table) {
        Empleado empleadoSeleccionado = table.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un empleado para editar.");
            alert.show();
            return;
        }

        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle("Editar Empleado");

        TextField txtNombre = new TextField(empleadoSeleccionado.getNombre());
        TextField txtRol = new TextField(empleadoSeleccionado.getRol());
        TextField txtHorario = new TextField(empleadoSeleccionado.getHorario());
        TextField txtSalario = new TextField(empleadoSeleccionado.getSalario());
        TextField txtTelefono = new TextField(empleadoSeleccionado.getTelefono());

        VBox content = new VBox(10, txtNombre, txtRol, txtHorario, txtSalario, txtTelefono);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Empleado(empleadoSeleccionado.getId(), txtNombre.getText(), txtRol.getText(),
                        txtHorario.getText(), txtSalario.getText(), txtTelefono.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(empleado -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "UPDATE Empleados SET nombre = ?, rol = ?, horario = ?, salario = ?, telefono = ? WHERE id_empleado = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, empleado.getNombre());
                    statement.setString(2, empleado.getRol());
                    statement.setString(3, empleado.getHorario());
                    statement.setString(4, empleado.getSalario());
                    statement.setString(5, empleado.getTelefono());
                    statement.setString(6, empleado.getId());
                    statement.executeUpdate();
                    cargarEmpleados(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void eliminarEmpleado(TableView<Empleado> table) {
        Empleado empleadoSeleccionado = table.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un empleado para eliminar.");
            alert.show();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar el empleado?");
        confirmacion.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection != null) {
                        String query = "DELETE FROM Empleados WHERE id_empleado = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, empleadoSeleccionado.getId());
                        statement.executeUpdate();
                        cargarEmpleados(table);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
