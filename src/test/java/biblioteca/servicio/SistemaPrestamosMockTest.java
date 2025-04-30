package biblioteca.servicio;

import biblioteca.modelo.Catalogo;
import biblioteca.modelo.Estado;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SistemaPrestamosMockTest {

    @Mock
    private Catalogo catalogo;

    @InjectMocks
    private SistemaPrestamos sistemaPrestamos;

    private Libro libroDisponible;
    private Libro libroPrestado;
    private static final String ISBN_DISPONIBLE = "978-3-16-148410-0";
    private static final String ISBN_PRESTADO = "978-0-13-235088-4";
    private static final String ISBN_NO_EXISTENTE = "isbn-no-existe";

    @BeforeEach
    void setUp() {
        libroDisponible = new Libro(ISBN_DISPONIBLE, "Clean Code", "Robert C. Martin");
        libroPrestado = new Libro(ISBN_PRESTADO, "Clean Architecture", "Robert C. Martin");
        libroPrestado.prestar();
    }

    @Test
    void testPrestarLibroDisponible() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_DISPONIBLE)).thenReturn(libroDisponible);

        // When
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_DISPONIBLE);

        // Then
        assertNotNull(prestamo);
        verify(catalogo).buscarPorIsbn(ISBN_DISPONIBLE);
        assertEquals(Estado.PRESTADO, libroDisponible.getEstado());
        assertEquals(libroDisponible, prestamo.getLibro());
    }

    @Test
    void testPrestarLibroNoDisponible() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_PRESTADO)).thenReturn(libroPrestado);

        // When
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_PRESTADO);

        // Then
        assertNull(prestamo);
        verify(catalogo).buscarPorIsbn(ISBN_PRESTADO);
        assertEquals(Estado.PRESTADO, libroPrestado.getEstado());
    }

    @Test
    void testPrestarLibroNoExistente() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_NO_EXISTENTE)).thenReturn(null);

        // When
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_NO_EXISTENTE);

        // Then
        assertNull(prestamo);
        verify(catalogo).buscarPorIsbn(ISBN_NO_EXISTENTE);
    }

    @Test
    void testDevolverLibroExistente() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_DISPONIBLE)).thenReturn(libroDisponible);
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_DISPONIBLE);

        // When
        boolean resultado = sistemaPrestamos.devolverLibro(prestamo);

        // Then
        assertTrue(resultado);
        assertEquals(Estado.DISPONIBLE, libroDisponible.getEstado());
        assertNotNull(prestamo.getFechaDevolucion());
    }

    @Test
    void testDevolverLibroPorIsbnExistente() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_DISPONIBLE)).thenReturn(libroDisponible);
        sistemaPrestamos.prestarLibro(ISBN_DISPONIBLE);

        // When
        boolean resultado = sistemaPrestamos.devolverLibro(ISBN_DISPONIBLE);

        // Then
        assertTrue(resultado);
        assertEquals(Estado.DISPONIBLE, libroDisponible.getEstado());
    }

    @Test
    void testDevolverLibroPorIsbnNoExistente() {
        // Given
        // No hay préstamos previos para el ISBN que intentamos devolver

        // When
        boolean resultado = sistemaPrestamos.devolverLibro(ISBN_NO_EXISTENTE);

        // Then
        assertFalse(resultado);
    }

    @Test
    void testObtenerPrestamosActivos() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_DISPONIBLE)).thenReturn(libroDisponible);
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_DISPONIBLE);

        // When
        List<Prestamo> prestamosActivos = sistemaPrestamos.obtenerPrestamosActivos();

        // Then
        assertEquals(1, prestamosActivos.size());
        assertTrue(prestamosActivos.contains(prestamo));

        // Devolvemos el libro
        sistemaPrestamos.devolverLibro(prestamo);

        // Verificamos que ya no está en la lista de activos
        prestamosActivos = sistemaPrestamos.obtenerPrestamosActivos();
        assertEquals(0, prestamosActivos.size());
    }

    @Test
    void testBuscarPrestamoActivoPorIsbn() {
        // Given
        when(catalogo.buscarPorIsbn(ISBN_DISPONIBLE)).thenReturn(libroDisponible);
        Prestamo prestamo = sistemaPrestamos.prestarLibro(ISBN_DISPONIBLE);

        // When
        Prestamo encontrado = sistemaPrestamos.buscarPrestamoActivoPorIsbn(ISBN_DISPONIBLE);

        // Then
        assertNotNull(encontrado);
        assertEquals(prestamo, encontrado);

        // Devolvemos el libro
        sistemaPrestamos.devolverLibro(prestamo);

        // Verificamos que ya no se encuentra como préstamo activo
        encontrado = sistemaPrestamos.buscarPrestamoActivoPorIsbn(ISBN_DISPONIBLE);
        assertNull(encontrado);
    }
}