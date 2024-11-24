package com.ecodeup.jdbc;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseUtils {

    public static void renumerarIDs() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // Renumerar Empleados
                renumerarTabla(connection, "Empleados", "id_empleado", "nombre, rol, horario, salario, telefono");

                // Renumerar Clientes
                renumerarTabla(connection, "Clientes", "id_cliente", "nombre, cedula, direccion, telefono, correo, historial_compras");

                // Renumerar Productos
                renumerarTabla(connection, "Productos", "id_producto", "nombre, descripcion, precio, categoria, existencias, id_proveedor");

                // Renumerar Proveedores
                renumerarTabla(connection, "Proveedores", "id_proveedor", "nombre, contacto, productos_suministrados, fecha_entrega");

                // Renumerar Ventas
                renumerarTabla(connection, "Ventas", "id_venta", "id_producto, id_cliente, fecha_hora, metodo_pago, id_empleado");

                System.out.println("Renumeración completada para todas las tablas.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void renumerarTabla(Connection connection, String tableName, String idColumn, String columns) throws Exception {
        Statement statement = connection.createStatement();

        // Deshabilitar claves foráneas
        statement.execute("SET FOREIGN_KEY_CHECKS = 0");

        String createTemp = String.format("CREATE TEMPORARY TABLE temp_%s AS SELECT * FROM %s ORDER BY %s", tableName, tableName, idColumn);
        String truncateTable = String.format("TRUNCATE TABLE %s", tableName);
        String insertData = String.format("INSERT INTO %s (%s) SELECT %s FROM temp_%s", tableName, columns, columns, tableName);
        String resetAutoIncrement = String.format("ALTER TABLE %s AUTO_INCREMENT = 1", tableName);

        statement.execute(createTemp);
        statement.execute(truncateTable);
        statement.execute(insertData);
        statement.execute(resetAutoIncrement);

        // Habilitar claves foráneas nuevamente
        statement.execute("SET FOREIGN_KEY_CHECKS = 1");

        System.out.println("Renumerados los IDs de la tabla: " + tableName);

        statement.close();
    }
}
