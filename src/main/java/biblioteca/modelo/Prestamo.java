package biblioteca.modelo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase que representa un préstamo de un libro
 */
public class Prestamo {
    private LocalDate fechaPrestamo;
    private Libro libro;
    private LocalDate fechaDevolucion;

    /**
     * Constructor de la clase Prestamo
     * 
     * @param libro el libro prestado
     */
    public Prestamo(Libro libro) {
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = null;
        libro.prestar();
    }

    /**
     * Constructor con fecha de préstamo manual (útil para pruebas)
     * 
     * @param libro         el libro prestado
     * @param fechaPrestamo la fecha del préstamo
     */
    public Prestamo(Libro libro, LocalDate fechaPrestamo) {
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
        libro.prestar();
    }

    /**
     * Devuelve el libro prestado
     * 
     * @return true si se pudo devolver, false si ya estaba devuelto
     */
    public boolean devolver() {
        if (fechaDevolucion != null) {
            return false;
        }
        libro.devolver();
        fechaDevolucion = LocalDate.now();
        return true;
    }

    /**
     * Devuelve el libro prestado en una fecha específica (útil para pruebas)
     * 
     * @param fechaDevolucion la fecha de devolución
     * @return true si se pudo devolver, false si ya estaba devuelto
     */
    public boolean devolver(LocalDate fechaDevolucion) {
        if (this.fechaDevolucion != null) {
            return false;
        }
        libro.devolver();
        this.fechaDevolucion = fechaDevolucion;
        return true;
    }

    /**
     * Obtiene la fecha del préstamo
     * 
     * @return la fecha del préstamo
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Obtiene el libro prestado
     * 
     * @return el libro prestado
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Obtiene la fecha de devolución
     * 
     * @return la fecha de devolución o null si no ha sido devuelto
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Verifica si el préstamo está activo (no ha sido devuelto)
     * 
     * @return true si está activo, false si ya fue devuelto
     */
    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(fechaPrestamo, prestamo.fechaPrestamo) &&
                Objects.equals(libro.getIsbn(), prestamo.libro.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaPrestamo, libro.getIsbn());
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "fechaPrestamo=" + fechaPrestamo +
                ", libro=" + libro +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}