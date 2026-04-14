package pe.idad.biblioteca.dto.response;

import lombok.Data;

@Data
public class LibroResponse {

    //ID del libro en la base de datos
    private Long id;

    //Título del libro
    private String titulo;

    //Autor del libro
    private String autor;

    // Indica si el libro está disponible para préstamo o no
    private boolean disponible;
}