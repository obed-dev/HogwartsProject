## Sistema Automatizado de Peajes 🚗
Este es un proyecto desarrollado en Java que implementa un sistema automatizado de cobro de peajes. El sistema está diseñado para clasificar vehículos, calcular tarifas automáticamente, generar informes diarios y ofrecer una interfaz gráfica de usuario (GUI) para una experiencia intuitiva y funcional.

## 📝 Características principales
Clasificación de vehículos: Soporte para distintos tipos de vehículos, incluyendo:
🚛 Camiones (Tarifa: 1000 colones)
🏍️ Motocicletas (Tarifa: 400 colones)
🚗 Automóviles (Tarifa: 600 colones)
Cálculo automático de tarifas: El sistema calcula y muestra la tarifa a pagar para cada vehículo de manera automática.

## Interfaz gráfica:
Menú desplegable para seleccionar el tipo de vehículo.
Botón para simular la entrada de vehículos al peaje.
Generación de reportes mediante un botón.
Reportes diarios:
Registro detallado de las transacciones del día.
Ordenamiento de vehículos por tipo.
Conteo total de vehículos procesados.
Persistencia de datos: Los datos se almacenan y gestionan usando estructuras como arrays, listas, pilas, colas, grafos y árboles.

## 🛠️ Tecnologías utilizadas
Lenguaje: Java
GUI: Swing (JOptionPane)
Conceptos clave:
Estructuras de datos: Arrays, pilas, colas, árboles, grafos.
Programación orientada a objetos: Composición, herencia y polimorfismo mediante interfaces y clases abstractas.

# 🚀 Cómo usar
Clona este repositorio en tu máquina local:
git clone https://github.com/obed-dev/JavaProyectoPeajes.git
Abre el proyecto en tu IDE preferido (por ejemplo, IntelliJ IDEA o Eclipse).
Ejecuta la clase principal Main.java.
Usa la interfaz gráfica para:
Seleccionar un tipo de vehículo.
Simular el paso de vehículos por el peaje.
Generar un reporte del día.


## 📂 Estructura del proyecto
JavaProyectoPeajes/
├── src/
│   ├── modelo/
│   │   ├── Vehiculo.java
│   │   ├── Camion.java
│   │   ├── Automovil.java
│   │   ├── Motocicleta.java
│   │   └── ...
│   ├── vista/
│   │   ├── InterfazGrafica.java
│   │   └── ...
│   ├── controlador/
│   │   ├── SimulacionPeaje.java
│   │   ├── GeneradorReportes.java
│   │   └── ...
│   └── Main.java
├── README.md
└── ...


# 📋 Requisitos del sistema
Java Development Kit (JDK) 8 o superior.
Cualquier IDE compatible con Java.
# 📈 Mejoras futuras
Integración de una base de datos para persistir los datos de forma permanente.
Implementación de gráficos en los reportes para mostrar estadísticas visuales.
Soporte para más tipos de vehículos y tarifas personalizadas.

# 🖋️ Autor
Obed Baltodano
GitHub

# 🤝 Contribuciones
Las contribuciones son bienvenidas. Si tienes ideas o mejoras, no dudes en abrir un issue o enviar un pull request.
