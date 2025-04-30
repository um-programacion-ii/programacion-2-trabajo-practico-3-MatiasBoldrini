package biblioteca.servicio;

import biblioteca.modelo.Catalogo;
import biblioteca.modelo.Estado;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que gestiona los préstamos de libros
 */
public class SistemaPrestamos {
    private Catalogo catalogo;
    private List<Prestamo> prestamos;

    /**
     * Constructor de la clase SistemaPrestamos
     * 
     * @param catalogo el catálogo de libros
     */
    public SistemaPrestamos(Catalogo catalogo) {
        this.catalogo = catalogo;
        this.prestamos = new ArrayList<>();
    }

    /**
     * Presta un libro por su ISBN
     * 
     * @param isbn el ISBN del libro a prestar
     * @return el préstamo creado o null si el libro no existe o no está disponible
     */
    public Prestamo prestarLibro(String isbn) {
        Libro libro = catalogo.buscarPorIsbn(isbn);

        if (libro == null) {
            System.out.println("El libro no existe en el catálogo");
            return null;
        }

        if (libro.getEstado() != Estado.DISPONIBLE) {
            System.out.println("El libro no está disponible para préstamo");
            return null;
        }

        Prestamo prestamo = new Prestamo(libro);
        prestamos.add(prestamo);
        return prestamo;
    }

    /**
     * Devuelve un libro prestado
     * 
     * @param prestamo el préstamo a devolver
     * @return true si se pudo devolver, false en caso contrario
     */
    public boolean devolverLibro(Prestamo prestamo) {
        if (!prestamos.contains(prestamo)) {
            System.out.println("El préstamo no existe en el sistema");
            return false;
        }

        return prestamo.devolver();
    }

    /**
     * Devuelve un libro por su ISBN
     * 
     * @param isbn el ISBN del libro a devolver
     * @return true si se pudo devolver, false en caso contrario
     */
    public boolean devolverLibro(String isbn) {
        Prestamo prestamo = buscarPrestamoActivoPorIsbn(isbn);

        if (prestamo == null) {
            System.out.println("No hay préstamos activos para ese libro");
            return false;
        }

        return prestamo.devolver();
    }

    /**
     * Busca un préstamo activo por el ISBN del libro
     * 
     * @param isbn el ISBN del libro prestado
     * @return el préstamo activo o null si no existe
     */
    public Prestamo buscarPrestamoActivoPorIsbn(String isbn) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getIsbn().equals(isbn) && prestamo.estaActivo()) {
                return prestamo;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los préstamos registrados
     * 
     * @return lista de préstamos
     */
    public List<Prestamo> obtenerTodosPrestamos() {
        return new ArrayList<>(prestamos);
    }

    /**
     * Obtiene los préstamos activos (no devueltos)
     * 
     * @return lista de préstamos activos
     */
    public List<Prestamo> obtenerPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();

        for (Prestamo prestamo : prestamos) {
            if (prestamo.estaActivo()) {
                activos.add(prestamo);
            }
        }

        return activos;
    }

    /**
     * Obtiene los préstamos realizados en una fecha específica
     * 
     * @param fecha la fecha de los préstamos a buscar
     * @return lista de préstamos en esa fecha
     */
    public List<Prestamo> obtenerPrestamosPorFecha(LocalDate fecha) {
        return prestamos.stream()
                .filter(p -> p.getFechaPrestamo().equals(fecha))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el catálogo asociado a este sistema de préstamos
     * 
     * @return el catálogo de libros
     */
    public Catalogo getCatalogo() {
        return catalogo;
    }
}