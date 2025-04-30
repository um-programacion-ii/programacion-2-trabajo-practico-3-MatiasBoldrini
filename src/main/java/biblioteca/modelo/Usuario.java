package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un usuario de la biblioteca
 */
public class Usuario {
    private String nombre;
    private String email;
    private List<Prestamo> historialPrestamos;

    /**
     * Constructor de la clase Usuario
     * 
     * @param nombre el nombre del usuario
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.email = null; // Opcional
        this.historialPrestamos = new ArrayList<>();
    }

    /**
     * Constructor completo de la clase Usuario
     * 
     * @param nombre el nombre del usuario
     * @param email  el email del usuario
     */
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.historialPrestamos = new ArrayList<>();
    }

    /**
     * Registra un préstamo para el usuario
     * 
     * @param prestamo el préstamo a registrar
     */
    public void registrarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            historialPrestamos.add(prestamo);
        }
    }

    /**
     * Obtiene el nombre del usuario
     * 
     * @return el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario
     * 
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del usuario
     * 
     * @return el email del usuario o null si no tiene
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario
     * 
     * @param email el nuevo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el historial de préstamos del usuario
     * 
     * @return lista con el historial de préstamos
     */
    public List<Prestamo> getHistorialPrestamos() {
        return new ArrayList<>(historialPrestamos);
    }

    /**
     * Verifica si el usuario tiene préstamos activos
     * 
     * @return true si tiene al menos un préstamo activo, false en caso contrario
     */
    public boolean tienePrestamosActivos() {
        for (Prestamo prestamo : historialPrestamos) {
            if (prestamo.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene los préstamos activos del usuario
     * 
     * @return lista de préstamos activos
     */
    public List<Prestamo> getPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();

        // Intencionalmente no uso streams para tener un código más "humano"
        for (Prestamo prestamo : historialPrestamos) {
            if (prestamo.estaActivo()) {
                activos.add(prestamo);
            }
        }

        return activos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", préstamos activos=" + getPrestamosActivos().size() +
                '}';
    }
}