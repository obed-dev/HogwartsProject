package com.ecodeup.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class VentasTab {

    public static BorderPane getVentasPane() {
        BorderPane pane = new BorderPane();

        // Crear tabla
        TableView<Venta> table = new TableView<>();
        TableColumn<Venta, String> colIdVenta = new TableColumn<>("ID Venta");
        TableColumn<Venta, String> colProducto = new TableColumn<>("Producto");
        TableColumn<Venta, String> colCliente = new TableColumn<>("Cliente");
        TableColumn<Venta, String> colFechaHora = new TableColumn<>("Fecha y Hora");
        TableColumn<Venta, String> colMetodoPago = new TableColumn<>("Método de Pago");
        TableColumn<Venta, String> colEmpleado = new TableColumn<>("Empleado");

        // Configurar columnas
        colIdVenta.setCellValueFactory(data -> data.getValue().idVentaProperty());
        colProducto.setCellValueFactory(data -> data.getValue().productoProperty());
        colCliente.setCellValueFactory(data -> data.getValue().clienteProperty());
        colFechaHora.setCellValueFactory(data -> data.getValue().fechaHoraProperty());
        colMetodoPago.setCellValueFactory(data -> data.getValue().metodoPagoProperty());
        colEmpleado.setCellValueFactory(data -> data.getValue().empleadoProperty());

        table.getColumns().addAll(colIdVenta, colProducto, colCliente, colFechaHora, colMetodoPago, colEmpleado);

        // Botones para acciones
        Button btnCargar = new Button("Cargar Ventas");
        Button btnAgregar = new Button("Agregar Venta");
        Button btnEditar = new Button("Editar Venta");
        Button btnEliminar = new Button("Eliminar Venta");
        Button btnReporteDiario = new Button("Reporte Diario");
        Button btnReporteSemanal = new Button("Reporte Semanal");
        Button btnReporteMensual = new Button("Reporte Mensual");
        Button btnClientesFrecuentes = new Button("Clientes Frecuentes");

        btnCargar.setOnAction(e -> cargarVentas(table));
        btnAgregar.setOnAction(e -> agregarVenta(table));
        btnEditar.setOnAction(e -> editarVenta(table));
        btnEliminar.setOnAction(e -> eliminarVenta(table));
        btnReporteDiario.setOnAction(e -> generarReporte("diario"));
        btnReporteSemanal.setOnAction(e -> generarReporte("semanal"));
        btnReporteMensual.setOnAction(e -> generarReporte("mensual"));
        btnClientesFrecuentes.setOnAction(e -> generarReporteClientesFrecuentes());

        // Layout
        HBox buttonBox = new HBox(10, btnCargar, btnAgregar, btnEditar, btnEliminar, btnReporteDiario, btnReporteSemanal, btnReporteMensual, btnClientesFrecuentes);
        VBox mainLayout = new VBox(10, table, buttonBox);

        pane.setCenter(mainLayout);

        return pane;
    }

    private static void cargarVentas(TableView<Venta> table) {
        ObservableList<Venta> ventas = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // Consulta SQL
                String query = "SELECT v.id_venta, v.fecha_hora, v.metodo_pago, p.nombre AS producto, " +
                        "c.nombre AS cliente, e.nombre AS empleado " +
                        "FROM Ventas v " +
                        "INNER JOIN Productos p ON v.id_producto = p.id_producto " +
                        "INNER JOIN Clientes c ON v.id_cliente = c.id_cliente " +
                        "INNER JOIN Empleados e ON v.id_empleado = e.id_empleado " +
                        "ORDER BY v.id_venta ASC";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Venta venta = new Venta(
                            resultSet.getString("id_venta"),
                            resultSet.getString("producto"),
                            resultSet.getString("cliente"),
                            resultSet.getString("fecha_hora"),
                            resultSet.getString("metodo_pago"),
                            resultSet.getString("empleado")
                    );
                    ventas.add(venta);
                }

                table.setItems(ventas);

                resultSet.close();
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void agregarVenta(TableView<Venta> table) {
        Dialog<Venta> dialog = new Dialog<>();
        dialog.setTitle("Agregar Venta");

        TextField txtProducto = new TextField();
        TextField txtCliente = new TextField();
        TextField txtMetodoPago = new TextField();
        TextField txtEmpleado = new TextField();

        txtProducto.setPromptText("ID Producto");
        txtCliente.setPromptText("ID Cliente");
        txtMetodoPago.setPromptText("Método de Pago");
        txtEmpleado.setPromptText("ID Empleado");

        VBox content = new VBox(10, txtProducto, txtCliente, txtMetodoPago, txtEmpleado);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Venta(null, txtProducto.getText(), txtCliente.getText(), null, txtMetodoPago.getText(), txtEmpleado.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(venta -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO Ventas (id_producto, id_cliente, metodo_pago, id_empleado) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, Integer.parseInt(venta.getProducto()));
                    statement.setInt(2, Integer.parseInt(venta.getCliente()));
                    statement.setString(3, venta.getMetodoPago());
                    statement.setInt(4, Integer.parseInt(venta.getEmpleado()));
                    statement.executeUpdate();
                    cargarVentas(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void editarVenta(TableView<Venta> table) {
        Venta ventaSeleccionada = table.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona una venta para editar.");
            alert.show();
            return;
        }

        Dialog<Venta> dialog = new Dialog<>();
        dialog.setTitle("Editar Venta");

        TextField txtProducto = new TextField(ventaSeleccionada.getProducto());
        TextField txtCliente = new TextField(ventaSeleccionada.getCliente());
        TextField txtMetodoPago = new TextField(ventaSeleccionada.getMetodoPago());
        TextField txtEmpleado = new TextField(ventaSeleccionada.getEmpleado());

        VBox content = new VBox(10, txtProducto, txtCliente, txtMetodoPago, txtEmpleado);
        dialog.getDialogPane().setContent(content);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new Venta(ventaSeleccionada.getIdVenta(), txtProducto.getText(), txtCliente.getText(), null, txtMetodoPago.getText(), txtEmpleado.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(venta -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String query = "UPDATE Ventas SET id_producto = ?, id_cliente = ?, metodo_pago = ?, id_empleado = ? WHERE id_venta = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, Integer.parseInt(venta.getProducto()));
                    statement.setInt(2, Integer.parseInt(venta.getCliente()));
                    statement.setString(3, venta.getMetodoPago());
                    statement.setInt(4, Integer.parseInt(venta.getEmpleado()));
                    statement.setInt(5, Integer.parseInt(venta.getIdVenta()));
                    statement.executeUpdate();
                    cargarVentas(table);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void eliminarVenta(TableView<Venta> table) {
        Venta ventaSeleccionada = table.getSelectionModel().getSelectedItem();
        if (ventaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona una venta para eliminar.");
            alert.show();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar la venta?");
        confirmacion.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection != null) {
                        String query = "DELETE FROM Ventas WHERE id_venta = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, Integer.parseInt(ventaSeleccionada.getIdVenta()));
                        statement.executeUpdate();
                        cargarVentas(table);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void generarReporte(String periodo) {
        String whereClause = "";
        switch (periodo) {
            case "diario":
                whereClause = "WHERE DATE(v.fecha_hora) = CURDATE()";
                break;
            case "semanal":
                whereClause = "WHERE YEARWEEK(v.fecha_hora, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "mensual":
                whereClause = "WHERE MONTH(v.fecha_hora) = MONTH(CURDATE()) AND YEAR(v.fecha_hora) = YEAR(CURDATE())";
                break;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT v.id_venta, v.fecha_hora, v.metodo_pago, p.nombre AS producto, " +
                        "c.nombre AS cliente, e.nombre AS empleado " +
                        "FROM Ventas v " +
                        "INNER JOIN Productos p ON v.id_producto = p.id_producto " +
                        "INNER JOIN Clientes c ON v.id_cliente = c.id_cliente " +
                        "INNER JOIN Empleados e ON v.id_empleado = e.id_empleado " +
                        whereClause;

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                FileWriter writer = new FileWriter("reporte_" + periodo + ".csv");
                writer.write("ID Venta,Fecha y Hora,Método de Pago,Producto,Cliente,Empleado\n");

                while (resultSet.next()) {
                    writer.write(resultSet.getInt("id_venta") + "," +
                            resultSet.getTimestamp("fecha_hora") + "," +
                            resultSet.getString("metodo_pago") + "," +
                            resultSet.getString("producto") + "," +
                            resultSet.getString("cliente") + "," +
                            resultSet.getString("empleado") + "\n");
                }

                writer.close();
                resultSet.close();
                statement.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reporte generado: reporte_" + periodo + ".csv");
                alert.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void generarReporteClientesFrecuentes() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT c.nombre, c.telefono, COUNT(v.id_venta) AS total_compras " +
                        "FROM Clientes c " +
                        "INNER JOIN Ventas v ON c.id_cliente = v.id_cliente " +
                        "GROUP BY c.id_cliente " +
                        "ORDER BY total_compras DESC";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                FileWriter writer = new FileWriter("reporte_clientes_frecuentes.csv");
                writer.write("Nombre,Teléfono,Total Compras\n");

                while (resultSet.next()) {
                    writer.write(resultSet.getString("nombre") + "," +
                            resultSet.getString("telefono") + "," +
                            resultSet.getInt("total_compras") + "\n");
                }

                writer.close();
                resultSet.close();
                statement.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reporte generado: reporte_clientes_frecuentes.csv");
                alert.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
