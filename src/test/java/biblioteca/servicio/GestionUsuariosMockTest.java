package biblioteca.servicio;

import biblioteca.modelo.Catalogo;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GestionUsuariosMockTest {

    @Mock
    private Catalogo catalogo;

    @Mock
    private SistemaPrestamos sistemaPrestamos;

    @InjectMocks
    private GestionUsuarios gestionUsuarios;

    private Libro libro;
    private Prestamo prestamo;
    private static final String NOMBRE_USUARIO = "usuario1";
    private static final String ISBN = "978-3-16-148410-0";

    @BeforeEach
    void setUp() {
        libro = new Libro(ISBN, "Clean Code", "Robert C. Martin");
        prestamo = new Prestamo(libro);
    }

    @Test
    void testRegistrarUsuario() {
        // When
        Usuario usuario = gestionUsuarios.registrarUsuario(NOMBRE_USUARIO);

        // Then
        assertNotNull(usuario);
        assertEquals(NOMBRE_USUARIO, usuario.getNombre());

        // Verificamos que se ha generado una notificación
        List<String> notificaciones = gestionUsuarios.getNotificaciones();
        assertEquals(1, notificaciones.size());
        assertTrue(notificaciones.get(0).contains(NOMBRE_USUARIO));
    }

    @Test
    void testRegistrarUsuarioConEmail() {
        // Given
        String email = "usuario1@example.com";

        // When
        Usuario usuario = gestionUsuarios.registrarUsuario(NOMBRE_USUARIO, email);

        // Then
        assertNotNull(usuario);
        assertEquals(NOMBRE_USUARIO, usuario.getNombre());
        assertEquals(email, usuario.getEmail());
    }

    @Test
    void testRegistrarPrestamo() {
        // Given
        gestionUsuarios.registrarUsuario(NOMBRE_USUARIO);
        // Utilizamos lenient para evitar "UnnecessaryStubbing"
        lenient().when(catalogo.buscarPorIsbn(ISBN)).thenReturn(libro);
        when(sistemaPrestamos.prestarLibro(ISBN)).thenReturn(prestamo);

        // When
        Prestamo resultado = gestionUsuarios.registrarPrestamo(NOMBRE_USUARIO, ISBN);

        // Then
        assertNotNull(resultado);
        verify(sistemaPrestamos).prestarLibro(ISBN);

        // Verificamos que el usuario tenga el préstamo en su historial
        Usuario usuario = gestionUsuarios.buscarUsuario(NOMBRE_USUARIO);
        assertEquals(1, usuario.getHistorialPrestamos().size());
    }

    @Test
    void testRegistrarPrestamoUsuarioNoExistente() {
        // No usamos stubbing innecesario aquí

        // When
        Prestamo resultado = gestionUsuarios.registrarPrestamo("usuario-no-existe", ISBN);

        // Then
        assertNull(resultado);
        // Verificamos que no se intentó prestar el libro
        verify(sistemaPrestamos, never()).prestarLibro(anyString());
    }

    @Test
    void testRegistrarDevolucion() {
        // Given
        gestionUsuarios.registrarUsuario(NOMBRE_USUARIO);
        when(sistemaPrestamos.devolverLibro(ISBN)).thenReturn(true);

        // When
        boolean resultado = gestionUsuarios.registrarDevolucion(NOMBRE_USUARIO, ISBN);

        // Then
        assertTrue(resultado);
        verify(sistemaPrestamos).devolverLibro(ISBN);
    }

    @Test
    void testRegistrarDevolucionUsuarioNoExistente() {
        // When
        boolean resultado = gestionUsuarios.registrarDevolucion("usuario-no-existe", ISBN);

        // Then
        assertFalse(resultado);
        // Verificamos que no se intentó devolver el libro
        verify(sistemaPrestamos, never()).devolverLibro(anyString());
    }

    @Test
    void testRegistrarDevolucionFallida() {
        // Given
        gestionUsuarios.registrarUsuario(NOMBRE_USUARIO);
        when(sistemaPrestamos.devolverLibro(ISBN)).thenReturn(false);

        // When
        boolean resultado = gestionUsuarios.registrarDevolucion(NOMBRE_USUARIO, ISBN);

        // Then
        assertFalse(resultado);
        verify(sistemaPrestamos).devolverLibro(ISBN);
    }

    @Test
    void testEliminarUsuarioSinPrestamos() {
        // Given
        gestionUsuarios.registrarUsuario(NOMBRE_USUARIO);

        // When
        boolean resultado = gestionUsuarios.eliminarUsuario(NOMBRE_USUARIO);

        // Then
        assertTrue(resultado);
        assertNull(gestionUsuarios.buscarUsuario(NOMBRE_USUARIO));
    }

    @Test
    void testEliminarUsuarioConPrestamoActivo() {
        // Given
        Usuario usuarioMock = mock(Usuario.class);
        // Eliminamos el stubbing innecesario
        when(usuarioMock.tienePrestamosActivos()).thenReturn(true);

        // Usamos reflexión para insertar el usuario mock en el mapa de usuarios
        try {
            java.lang.reflect.Field usuariosField = GestionUsuarios.class.getDeclaredField("usuarios");
            usuariosField.setAccessible(true);
            java.util.Map<String, Usuario> usuarios = (java.util.Map<String, Usuario>) usuariosField
                    .get(gestionUsuarios);
            usuarios.put(NOMBRE_USUARIO, usuarioMock);
        } catch (Exception e) {
            fail("Error al configurar el test: " + e.getMessage());
        }

        // When
        boolean resultado = gestionUsuarios.eliminarUsuario(NOMBRE_USUARIO);

        // Then
        assertFalse(resultado);
        assertNotNull(gestionUsuarios.buscarUsuario(NOMBRE_USUARIO));
    }

    @Test
    void testObtenerUsuariosConPrestamosActivos() {
        // Given
        Usuario usuario1 = new Usuario("usuario1");
        Usuario usuario2 = new Usuario("usuario2");
        Usuario usuario3 = new Usuario("usuario3");

        // Agregar los usuarios a la gestión
        try {
            java.lang.reflect.Field usuariosField = GestionUsuarios.class.getDeclaredField("usuarios");
            usuariosField.setAccessible(true);
            java.util.Map<String, Usuario> usuarios = (java.util.Map<String, Usuario>) usuariosField
                    .get(gestionUsuarios);
            usuarios.put("usuario1", usuario1);
            usuarios.put("usuario2", usuario2);
            usuarios.put("usuario3", usuario3);
        } catch (Exception e) {
            fail("Error al configurar el test: " + e.getMessage());
        }

        // Simular que solo los usuarios 1 y 3 tienen préstamos activos
        usuario1.registrarPrestamo(prestamo); // Este préstamo está activo por defecto

        Prestamo prestamoDevuelto = new Prestamo(libro);
        prestamoDevuelto.devolver(); // Este préstamo ya no está activo
        usuario2.registrarPrestamo(prestamoDevuelto);

        Prestamo prestamoActivo2 = new Prestamo(libro);
        usuario3.registrarPrestamo(prestamoActivo2);

        // When
        List<Usuario> usuariosActivos = gestionUsuarios.obtenerUsuariosConPrestamosActivos();

        // Then
        assertEquals(2, usuariosActivos.size());
        assertTrue(usuariosActivos.contains(usuario1));
        assertFalse(usuariosActivos.contains(usuario2));
        assertTrue(usuariosActivos.contains(usuario3));
    }
}