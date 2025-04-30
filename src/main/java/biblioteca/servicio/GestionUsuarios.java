package biblioteca.servicio;

import biblioteca.modelo.Catalogo;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que gestiona los usuarios de la biblioteca y sus préstamos
 */
public class GestionUsuarios {
    private Map<String, Usuario> usuarios;
    private Catalogo catalogo;
    private SistemaPrestamos sistemaPrestamos;

    // Usando una variable para notificaciones en lugar de un sistema más
    // sofisticado
    private List<String> notificaciones = new ArrayList<>();

    /**
     * Constructor de la clase GestionUsuarios
     * 
     * @param catalogo         el catálogo de libros
     * @param sistemaPrestamos el sistema de préstamos
     */
    public GestionUsuarios(Catalogo catalogo, SistemaPrestamos sistemaPrestamos) {
        this.usuarios = new HashMap<>();
        this.catalogo = catalogo;
        this.sistemaPrestamos = sistemaPrestamos;
    }

    /**
     * Registra un nuevo usuario
     * 
     * @param nombre nombre del usuario
     * @return el usuario creado
     */
    public Usuario registrarUsuario(String nombre) {
        if (usuarios.containsKey(nombre)) {
            System.out.println("El usuario ya existe");
            return usuarios.get(nombre);
        }

        Usuario nuevoUsuario = new Usuario(nombre);
        usuarios.put(nombre, nuevoUsuario);

        // Notificación simple
        String mensaje = "Nuevo usuario registrado: " + nombre;
        notificaciones.add(mensaje);
        System.out.println(mensaje);

        return nuevoUsuario;
    }

    /**
     * Registra un nuevo usuario con email
     * 
     * @param nombre nombre del usuario
     * @param email  email del usuario
     * @return el usuario creado
     */
    public Usuario registrarUsuario(String nombre, String email) {
        if (usuarios.containsKey(nombre)) {
            System.out.println("El usuario ya existe");
            return usuarios.get(nombre);
        }

        Usuario nuevoUsuario = new Usuario(nombre, email);
        usuarios.put(nombre, nuevoUsuario);

        // Notificación simple
        notificaciones.add("Nuevo usuario registrado: " + nombre + " (" + email + ")");

        return nuevoUsuario;
    }

    /**
     * Busca un usuario por su nombre
     * 
     * @param nombre nombre del usuario a buscar
     * @return el usuario encontrado o null si no existe
     */
    public Usuario buscarUsuario(String nombre) {
        return usuarios.get(nombre);
    }

    /**
     * Registra un préstamo para un usuario
     * 
     * @param nombreUsuario nombre del usuario
     * @param isbn          ISBN del libro a prestar
     * @return el préstamo creado o null si hubo algún problema
     */
    public Prestamo registrarPrestamo(String nombreUsuario, String isbn) {
        Usuario usuario = buscarUsuario(nombreUsuario);

        if (usuario == null) {
            System.out.println("El usuario no existe");
            return null;
        }

        Prestamo prestamo = sistemaPrestamos.prestarLibro(isbn);

        if (prestamo != null) {
            usuario.registrarPrestamo(prestamo);

            // Notificación simple
            String mensaje = "Préstamo registrado para " + nombreUsuario + ": " + isbn;
            notificaciones.add(mensaje);
            System.out.println(mensaje);
        }

        return prestamo;
    }

    /**
     * Registra la devolución de un libro
     * 
     * @param nombreUsuario nombre del usuario
     * @param isbn          ISBN del libro a devolver
     * @return true si se pudo devolver, false en caso contrario
     */
    public boolean registrarDevolucion(String nombreUsuario, String isbn) {
        Usuario usuario = buscarUsuario(nombreUsuario);

        if (usuario == null) {
            System.out.println("El usuario no existe");
            return false;
        }

        boolean devuelto = sistemaPrestamos.devolverLibro(isbn);

        if (devuelto) {
            // Notificación simple
            String mensaje = "Devolución registrada para " + nombreUsuario + ": " + isbn;
            notificaciones.add(mensaje);
            System.out.println(mensaje);
        }

        return devuelto;
    }

    /**
     * Obtiene todas las notificaciones generadas
     * 
     * @return lista de notificaciones
     */
    public List<String> getNotificaciones() {
        return new ArrayList<>(notificaciones);
    }

    /**
     * Obtiene todos los usuarios registrados
     * 
     * @return lista de usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    /**
     * Obtiene usuarios con préstamos activos
     * 
     * @return lista de usuarios con préstamos activos
     */
    public List<Usuario> obtenerUsuariosConPrestamosActivos() {
        List<Usuario> usuariosActivos = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            if (usuario.tienePrestamosActivos()) {
                usuariosActivos.add(usuario);
            }
        }

        return usuariosActivos;
    }

    /**
     * Elimina un usuario (si no tiene préstamos activos)
     * 
     * @param nombre nombre del usuario a eliminar
     * @return true si se pudo eliminar, false en caso contrario
     */
    public boolean eliminarUsuario(String nombre) {
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            return false;
        }

        if (usuario.tienePrestamosActivos()) {
            System.out.println("No se puede eliminar al usuario porque tiene préstamos activos");
            return false;
        }

        usuarios.remove(nombre);

        // Notificación simple
        String mensaje = "Usuario eliminado: " + nombre;
        notificaciones.add(mensaje);
        System.out.println(mensaje);

        return true;
    }
}