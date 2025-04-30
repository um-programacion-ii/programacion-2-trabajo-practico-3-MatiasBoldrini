package biblioteca.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {

    private Catalogo catalogo;
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
        libro1 = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro2 = new Libro("978-0-13-235088-4", "Clean Architecture", "Robert C. Martin");
        libro3 = new Libro("978-0-13-597445-9", "Design Patterns", "Erich Gamma");

        catalogo.agregarLibro(libro1);
        catalogo.agregarLibro(libro2);
        catalogo.agregarLibro(libro3);
    }

    @Test
    void testBuscarPorIsbn() {
        // When
        Libro libro = catalogo.buscarPorIsbn("978-3-16-148410-0");

        // Then
        assertNotNull(libro);
        assertEquals("Clean Code", libro.getTitulo());
    }

    @Test
    void testBuscarPorIsbnNoExistente() {
        // When
        Libro libro = catalogo.buscarPorIsbn("isbn-no-existente");

        // Then
        assertNull(libro);
    }

    @Test
    void testObtenerLibrosDisponibles() {
        // Given
        libro1.prestar(); // Prestamos un libro

        // When
        List<Libro> disponibles = catalogo.obtenerLibrosDisponibles();

        // Then
        assertEquals(2, disponibles.size());
        assertTrue(disponibles.contains(libro2));
        assertTrue(disponibles.contains(libro3));
        assertFalse(disponibles.contains(libro1));
    }

    @Test
    void testLibrosDisponiblesConStreamMethod() {
        // Given
        libro2.prestar(); // Prestamos un libro

        // When
        List<Libro> disponibles = catalogo.getLibrosDisponiblesStream();

        // Then
        assertEquals(2, disponibles.size());
        assertTrue(disponibles.contains(libro1));
        assertTrue(disponibles.contains(libro3));
        assertFalse(disponibles.contains(libro2));
    }

    @Test
    void testObtenerTodosLosLibros() {
        // When
        List<Libro> todos = catalogo.obtenerTodosLosLibros();

        // Then
        assertEquals(3, todos.size());
        assertTrue(todos.contains(libro1));
        assertTrue(todos.contains(libro2));
        assertTrue(todos.contains(libro3));
    }
}