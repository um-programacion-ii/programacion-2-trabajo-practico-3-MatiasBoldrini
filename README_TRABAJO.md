# Sistema de Gestión de Biblioteca

## Datos del Alumno
- **Nombre:** Matías
- **Apellido:** Boldrini
- **Legajo:** 61074

## Descripción del Proyecto
Este proyecto implementa un sistema de gestión para una biblioteca que permite administrar libros, préstamos y usuarios. Se aplicaron principios SOLID y buenas prácticas de programación orientada a objetos.

## Funcionalidades Principales
- Gestión de libros (agregar, buscar por ISBN)
- Catálogo con búsqueda de libros disponibles
- Sistema de préstamos y devoluciones
- Gestión de usuarios y su historial de préstamos
- Notificaciones básicas de eventos importantes

## Instrucciones para Ejecutar el Proyecto
1. Clonar el repositorio
2. Asegurarse de tener Java 21 o superior instalado
3. Compilar el proyecto con Maven:
   ```
   mvn clean install
   ```
4. Para ejecutar las pruebas:
   ```
   mvn test
   ```

## Estructura del Proyecto
El proyecto está organizado en los siguientes paquetes:

- `biblioteca.modelo`: Contiene las clases del modelo de dominio (Libro, Usuario, etc.)
- `biblioteca.servicio`: Contiene las clases que implementan la lógica de negocio

## Detalles de Implementación

### Clases Principales

#### Modelo
- **Libro**: Representa un libro con ISBN, título, autor y estado.
- **Usuario**: Representa un usuario con nombre y su historial de préstamos.
- **Prestamo**: Representa un préstamo con fecha, libro y estado.
- **Catalogo**: Gestiona la colección de libros disponibles.

#### Servicios
- **SistemaPrestamos**: Gestiona las operaciones de préstamo y devolución.
- **GestionUsuarios**: Administra los usuarios y sus acciones.

### Características Técnicas
- Uso de colecciones de Java (ArrayList, HashMap)
- Manejo de fechas con la API de tiempo de Java (LocalDate)
- Encapsulamiento adecuado de atributos
- Tests unitarios con JUnit y Mockito

## Limitaciones Conocidas
- La persistencia de datos no está implementada
- No hay interfaz gráfica, solo lógica de negocios
- El sistema de notificaciones es básico (solo por consola)

## Futuras Mejoras
- Implementar persistencia de datos en una base de datos
- Añadir una interfaz gráfica de usuario
- Mejorar el sistema de notificaciones con correos electrónicos
- Implementar más funcionalidades como reservas y categorías de libros 