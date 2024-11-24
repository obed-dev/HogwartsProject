package com.ecodeup.jdbc;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Actualizar inventarios al inicio del programa
        InventarioUpdater.actualizarInventarios();

        primaryStage.setTitle("Gestión de Hogwarts Store");

        // Crear el contenedor de pestañas
        TabPane tabPane = new TabPane();

        // Pestañas
        Tab clientesTab = new Tab("Clientes", ClientesTab.getClientesPane());
        Tab productosTab = new Tab("Productos", ProductosTab.getProductosPane());
        Tab inventariosTab = new Tab("Inventarios", InventariosTab.getInventariosPane());
        Tab empleadosTab = new Tab("Empleados", EmpleadosTab.getEmpleadosPane());
        Tab ventasTab = new Tab("Ventas", VentasTab.getVentasPane());
        Tab proveedoresTab = new Tab("Proveedores", ProveedoresTab.getProveedoresPane());



        clientesTab.setClosable(false);
        productosTab.setClosable(false);
        empleadosTab.setClosable(false);
        proveedoresTab.setClosable(false);
        ventasTab.setClosable(false);

        // Agregar pestañas al contenedor
        tabPane.getTabs().addAll(clientesTab, productosTab, empleadosTab, proveedoresTab, ventasTab , inventariosTab);

        // Crear la escena
        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);



    }
}
