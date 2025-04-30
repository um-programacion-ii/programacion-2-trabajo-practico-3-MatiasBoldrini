package biblioteca.modelo;

/**
 * Clase que representa un libro en el sistema de biblioteca
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private Estado estado;

    /**
     * Constructor de la clase Libro
     * 
     * @param isbn   el ISBN del libro
     * @param titulo el título del libro
     * @param autor  el autor del libro
     */
    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = Estado.DISPONIBLE; // Por defecto, un libro nuevo está disponible
    }

    // Getters y setters

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // Métodos de préstamo y devolución

    public void prestar() {
        if (estado == Estado.DISPONIBLE) {
            estado = Estado.PRESTADO;
        } else {
            System.out.println("El libro ya está prestado");
        }
    }

    public void devolver() {
        if (estado == Estado.PRESTADO) {
            estado = Estado.DISPONIBLE;
        } else {
            System.out.println("El libro ya está disponible");
        }
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", estado=" + estado +
                '}';
    }
}