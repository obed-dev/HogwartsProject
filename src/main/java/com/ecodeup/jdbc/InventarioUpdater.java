package com.ecodeup.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InventarioUpdater {

    public static void actualizarInventarios() {
        String truncateQuery = "TRUNCATE TABLE inventarios;";

        String insertQuery =
                "INSERT INTO inventarios (id_producto, cantidad_vendida, fecha_reposicion, existencias_actuales) " +
                        "SELECT p.id_producto, " +
                        "       COALESCE(SUM(CASE WHEN v.id_producto = p.id_producto THEN 1 ELSE 0 END), 0) AS cantidad_vendida, " +
                        "       CASE " +
                        "           WHEN (p.existencias - COALESCE(SUM(CASE WHEN v.id_producto = p.id_producto THEN 1 ELSE 0 END), 0)) < 5 " +
                        "           THEN CURDATE() + INTERVAL 7 DAY " +
                        "           ELSE NULL " +
                        "       END AS fecha_reposicion, " +
                        "       (p.existencias - COALESCE(SUM(CASE WHEN v.id_producto = p.id_producto THEN 1 ELSE 0 END), 0)) AS existencias_actuales " +
                        "FROM productos p " +
                        "LEFT JOIN ventas v ON p.id_producto = v.id_producto " +
                        "GROUP BY p.id_producto;";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // Vaciar la tabla inventarios
                try (PreparedStatement truncateStatement = connection.prepareStatement(truncateQuery)) {
                    truncateStatement.executeUpdate();
                    System.out.println("Tabla inventarios truncada correctamente.");
                }

                // Insertar los nuevos valores
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.executeUpdate();
                    System.out.println("Inventarios actualizados correctamente con existencias actuales.");
                }
            } else {
                System.out.println("No se pudo conectar a la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
