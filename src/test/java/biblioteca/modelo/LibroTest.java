package biblioteca.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    @Test
    void testCrearLibroValido() {
        // Given
        String isbn = "978-3-16-148410-0";
        String titulo = "Clean Code";
        String autor = "Robert C. Martin";

        // When
        Libro libro = new Libro(isbn, titulo, autor);

        // Then
        assertEquals(isbn, libro.getIsbn());
        assertEquals(titulo, libro.getTitulo());
        assertEquals(autor, libro.getAutor());
        assertEquals(Estado.DISPONIBLE, libro.getEstado());
    }

    @Test
    void testPrestarLibro() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        // When
        libro.prestar();

        // Then
        assertEquals(Estado.PRESTADO, libro.getEstado());
    }

    @Test
    void testDevolverLibro() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.prestar();

        // When
        libro.devolver();

        // Then
        assertEquals(Estado.DISPONIBLE, libro.getEstado());
    }

    @Test
    void testPrestarLibroYaPrestado() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.prestar();

        // When
        libro.prestar(); // Intentamos prestar nuevamente

        // Then
        assertEquals(Estado.PRESTADO, libro.getEstado()); // El estado debe seguir siendo PRESTADO
    }

    @Test
    void testDevolverLibroYaDisponible() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        // When
        libro.devolver(); // Intentamos devolver un libro que ya está disponible

        // Then
        assertEquals(Estado.DISPONIBLE, libro.getEstado()); // El estado debe seguir siendo DISPONIBLE
    }

    @Test
    void testCambiarTitulo() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        String nuevoTitulo = "Clean Code: A Handbook of Agile Software Craftsmanship";

        // When
        libro.setTitulo(nuevoTitulo);

        // Then
        assertEquals(nuevoTitulo, libro.getTitulo());
    }

    @Test
    void testCambiarAutor() {
        // Given
        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        String nuevoAutor = "Uncle Bob";

        // When
        libro.setAutor(nuevoAutor);

        // Then
        assertEquals(nuevoAutor, libro.getAutor());
    }
}