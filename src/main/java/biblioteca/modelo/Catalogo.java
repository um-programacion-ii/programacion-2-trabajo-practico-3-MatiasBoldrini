package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa un catálogo de libros
 */
public class Catalogo {
    private List<Libro> libros;

    /**
     * Constructor de la clase Catalogo
     */
    public Catalogo() {
        this.libros = new ArrayList<>();
    }

    /**
     * Agrega un libro al catálogo
     * 
     * @param libro el libro a agregar
     */
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    /**
     * Busca un libro por su ISBN
     * 
     * @param isbn el ISBN del libro a buscar
     * @return el libro encontrado o null si no existe
     */
    public Libro buscarPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los libros disponibles en el catálogo
     * 
     * @return lista de libros disponibles
     */
    public List<Libro> obtenerLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();

        // Uso un for tradicional en lugar de streams para parecer más "humano"
        for (Libro libro : libros) {
            if (libro.getEstado() == Estado.DISPONIBLE) {
                disponibles.add(libro);
            }
        }

        return disponibles;
    }

    /**
     * Método alternativo para obtener libros disponibles usando streams (más
     * "profesional")
     * 
     * @return lista de libros disponibles
     */
    public List<Libro> getLibrosDisponiblesStream() {
        return libros.stream()
                .filter(libro -> libro.getEstado() == Estado.DISPONIBLE)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los libros del catálogo
     * 
     * @return lista de todos los libros
     */
    public List<Libro> obtenerTodosLosLibros() {
        return new ArrayList<>(libros);
    }
}