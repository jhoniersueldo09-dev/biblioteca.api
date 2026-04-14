package pe.idad.biblioteca.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrestamoResponse {

    //ID del préstamo
    private Long id;

    //Fecha en la que se realizó el préstamo del libro
    private LocalDate fechaPrestamo;

    //Fecha en la que se debe devolver el libro o fue devuelto
    private LocalDate fechaDevolucion;

    //ID del usuario que realizó el préstamo
    private String usuario;

    //ID del libro prestado
    private String libro;
}