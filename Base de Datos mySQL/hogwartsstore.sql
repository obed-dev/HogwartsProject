-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-11-2024 a las 19:11:08
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `hogwartsstore`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--
CREATE  DATABASE hogwartsstore;
USE hogwartsstore;



CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `cedula` varchar(20) NOT NULL,
  `direccion` text DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `historial_compras` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre`, `cedula`, `direccion`, `telefono`, `correo`, `historial_compras`) VALUES
(1, 'Harry Potter', '001', 'Privet Drive, Londres', '111-222-3333', 'harry@hogwarts.com', 'Varita de Saúco, Chocolate de Rana'),
(2, 'Luna Lovegood', '002', 'Ottery St Catchpole', '444-555-6666', 'luna@hogwarts.com', 'Libro: Hechizos Avanzados'),
(3, 'Bellatrix Lestrange', '003', NULL, '111-4452-235', 'bellatrix@gmail.com', NULL),
(4, 'Neville Longbutton', '004', NULL, '456-500-900', 'nevilleLongbutton@gmail.com', NULL),
(5, 'Sirius Black', '009', 'Privet Drive, Londres', '111-222-3333', 'sirius@hogwarts.com', 'Varita de Saúco, Chocolate de Rana'),
(6, 'Lucius Malfoy', '013', 'Ottery St Catchpole', '444-555-8989', 'malfoy@hogwarts.com', 'Libro: Hechizos Avanzados'),
(7, 'Hagrid', '011', NULL, '200-500-630', 'hagridhogwarts@gmail.com', NULL),
(8, 'Jenny Weasley', '013', 'Privet Drive, Londres', '111-222-3389', 'jenny@hogwarts.com', 'Varita de Saúco, Chocolate de Rana'),
(9, 'Draco Malfoy', '012', 'Ottery St Catchpole', '444-555-6666', 'malfoy@hogwarts.com', 'Libro: Hechizos Avanzados'),
(10, 'Dumbledore', '0116', NULL, '200-500-2014', 'dumbledorehogwarts@gmail.com', NULL),
(11, 'Lord Voldemort', '016', NULL, '500-200-150', 'LordVoldemort@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id_empleado` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `rol` varchar(50) DEFAULT NULL,
  `horario` varchar(50) DEFAULT NULL,
  `salario` decimal(10,2) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id_empleado`, `nombre`, `rol`, `horario`, `salario`, `telefono`) VALUES
(1, 'Hermione Granger', 'Gerente', '9:00 - 17:00', 1500.00, '123-456-7890'),
(2, 'Ron Weasley', 'Vendedor', '10:00 - 18:00', 900.00, '234-567-8901'),
(3, 'Neville Longbutton', 'Vendedor', '10:00 - 18:00', 900.00, '456-500-900'),
(4, 'Fred Weasley', 'Gerente', '9:00 - 17:00', 1500.00, '123-456-7890'),
(5, 'Luna Lovegood', 'Vendedor', '10:00 - 18:00', 900.00, '234-567-8901'),
(6, 'George Weasley', 'Vendedor', '9:00 - 17:00', 900.00, '123-456-7890'),
(7, 'Jenny Weasley', 'Vendedor', '10:00 - 18:00', 900.00, '234-567-8901');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventarios`
--

CREATE TABLE `inventarios` (
  `id_inventario` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad_vendida` int(11) NOT NULL DEFAULT 0,
  `fecha_reposicion` date DEFAULT NULL,
  `existencias_actuales` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventarios`
--

INSERT INTO `inventarios` (`id_inventario`, `id_producto`, `cantidad_vendida`, `fecha_reposicion`, `existencias_actuales`) VALUES
(1, 1, 5, NULL, 5),
(2, 2, 4, NULL, 46),
(3, 3, 3, NULL, 17),
(4, 4, 0, '2024-11-27', 2),
(5, 5, 0, NULL, 10),
(6, 6, 0, NULL, 50),
(7, 7, 0, '2024-11-27', 1),
(8, 8, 4, NULL, 6),
(9, 9, 0, NULL, 50),
(10, 10, 1, NULL, 19),
(11, 11, 0, NULL, 10),
(12, 12, 0, NULL, 50),
(13, 13, 0, NULL, 20),
(14, 14, 0, NULL, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `categoria` varchar(50) NOT NULL,
  `existencias` int(11) NOT NULL,
  `id_proveedor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre`, `descripcion`, `precio`, `categoria`, `existencias`, `id_proveedor`) VALUES
(1, 'Varita de Saúco', 'La varita mágica más poderosa.', 1500.00, 'Magia', 10, 2),
(2, 'Chocolate de Rana', 'Dulces mágicos con tarjetas coleccionables.', 10.00, 'Dulces', 50, 3),
(3, 'Libro: Hechizos Avanzados', 'Para magos avanzados.', 75.00, 'Libros', 20, 1),
(4, 'Nimbus 3000', 'Escoba Voladora', 5000.00, 'Transporte', 2, NULL),
(5, 'Pocion Magica de Amor', 'Pocion magica más poderosa para enamorar.', 500.00, 'Magia', 10, 2),
(6, 'Dulces Picosos', 'Dulces mágicos con tarjetas coleccionables.', 10.00, 'Dulces', 50, 3),
(7, 'Orrocrux', 'Artefacto que divide el alma', 1000000.00, 'Magia', 1, NULL),
(8, 'Capa Invisibilidad', 'Capa que te hace invisible.', 800.00, 'Magia', 10, 2),
(9, 'Chocolates Amargos', 'Dulces mágicos con tarjetas coleccionables.', 10.00, 'Dulces', 50, 3),
(10, 'Libro: Hechizos Avanzados de Herbolaria', 'Para magos avanzados.', 75.00, 'Libros', 20, 1),
(11, 'Escoba para Quiditch.', 'Escoba para volar y hacer deporte.', 1000.00, 'Magia', 10, 2),
(12, 'Pocion de la Verdad', 'Pocion poderosa para revelar la verdad ', 20.00, 'Magia', 50, 3),
(13, 'Libro: Hechizos Defensa ', 'Para magos avanzados.', 75.00, 'Libros', 20, 1),
(14, 'Escoba Voladora', 'Escoba de transporte', 1000.00, 'Magia', 10, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id_proveedor` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `contacto` varchar(100) DEFAULT NULL,
  `productos_suministrados` text DEFAULT NULL,
  `fecha_entrega` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id_proveedor`, `nombre`, `contacto`, `productos_suministrados`, `fecha_entrega`) VALUES
(1, 'Flourish and Blotts', 'flourish@hogwartsstore.com', 'Libros de hechizos, pergaminos', '2024-11-20'),
(2, 'Ollivanders', 'ollivanders@hogwartsstore.com', 'Varitas mágicas', '2024-11-22'),
(3, 'Honeydukes', 'honeydukes@hogwartsstore.com', 'Dulces mágicos', '2024-11-18'),
(4, 'Bolsa de Té de Rosa Lee', 'bolsadeTe@hogwartsstore.com', 'Lugar de bebidas y dulces', '2024-11-20'),
(5, 'Heladería Florean Fortescue', 'FloreanFortescue@hogwartsstore.com', 'Lugar de venta de Helados', '2024-11-22'),
(6, 'McBloom & McMuck', 'McBloom&McMuck@hogwartsstore.com', 'Tienda Souvenir', '2024-11-18'),
(7, 'Brews & Stews Café', 'Brews&Stews@hogwartsstore.com', 'Cafeteria', '2024-11-20'),
(8, 'Tienda de plumas Scrivenshaft', 'Scrivenshaft@hogwartsstore.com', 'Tienda de Plumas', '2024-11-22'),
(9, 'Pedrus Pridgeon', 'PedrusPridgeon@hogwartsstore.com', 'Dulces mágicos', '2024-11-18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `fecha_hora` datetime NOT NULL DEFAULT current_timestamp(),
  `metodo_pago` varchar(50) DEFAULT NULL,
  `id_empleado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `id_producto`, `id_cliente`, `fecha_hora`, `metodo_pago`, `id_empleado`) VALUES
(1, 1, 1, '2024-11-18 22:44:24', 'Tarjeta de crédito', 1),
(2, 2, 1, '2024-11-18 22:44:24', 'Efectivo', 2),
(3, 3, 2, '2024-11-18 22:44:24', 'Tarjeta de débito', 1),
(4, 1, 1, '2024-11-19 14:57:46', 'Tarjeta de crédito', 1),
(5, 2, 1, '2024-11-19 14:57:46', 'Efectivo', 2),
(6, 1, 1, '2024-11-19 15:27:40', 'Efectivo', 2),
(7, 8, 1, '2024-11-19 15:35:31', 'Tarjeta de Credito', 1),
(8, 8, 1, '2024-11-19 15:36:32', 'Efectivo', 1),
(9, 1, 1, '2024-11-19 16:26:55', 'Tarjeta de crédito', 1),
(10, 2, 1, '2024-11-19 16:26:55', 'Efectivo', 2),
(11, 3, 2, '2024-11-19 16:26:55', 'Tarjeta de débito', 1),
(12, 8, 1, '2024-11-19 16:31:05', 'Tarjeta de Credito', 3),
(13, 8, 1, '2024-11-19 16:36:06', 'Tarjeta de Debito', 4),
(14, 1, 1, '2024-11-19 16:38:09', 'Tarjeta de crédito', 1),
(15, 2, 1, '2024-11-19 16:38:09', 'Efectivo', 2),
(16, 3, 2, '2024-11-19 16:38:09', 'Tarjeta de débito', 1),
(17, 10, 6, '2024-11-19 19:20:23', 'Tarjeta de Credito', 5);

--
-- Disparadores `ventas`
--
DELIMITER $$
CREATE TRIGGER `actualizar_inventario_al_vender` AFTER INSERT ON `ventas` FOR EACH ROW BEGIN
    -- Actualizar la cantidad vendida en la tabla inventarios
    UPDATE inventarios
    SET cantidad_vendida = cantidad_vendida + 1
    WHERE id_producto = NEW.id_producto;

    -- Restar las existencias del producto en la tabla productos
    UPDATE productos
    SET existencias = existencias - 1
    WHERE id_producto = NEW.id_producto;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id_empleado`);

--
-- Indices de la tabla `inventarios`
--
ALTER TABLE `inventarios`
  ADD PRIMARY KEY (`id_inventario`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `id_proveedor` (`id_proveedor`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `inventarios`
--
ALTER TABLE `inventarios`
  MODIFY `id_inventario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inventarios`
--
ALTER TABLE `inventarios`
  ADD CONSTRAINT `inventarios_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`),
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
