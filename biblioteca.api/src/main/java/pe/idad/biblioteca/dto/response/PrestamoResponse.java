package pe.idad.biblioteca.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PrestamoResponse {

    // ID del préstamo
    private Long id;

    // Fecha en la que se realizó el préstamo
    private LocalDate fechaPrestamo;

    // Fecha en la que se devolvió el libro (null si aún no se devuelve)
    private LocalDate fechaDevolucion;

    // Información del usuario (ej: email o nombre)
    private String usuario;

    // Información del libro (ej: título)
    private String libro;
}