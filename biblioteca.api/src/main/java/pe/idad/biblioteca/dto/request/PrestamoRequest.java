package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrestamoRequest {

    // Fecha en la que se realiza el préstamo
    @NotNull
    private LocalDate fechaPrestamo;

    // Fecha estimada o real de devolución del libro
    private LocalDate fechaDevolucion;

    // ID del usuario que solicita el préstamo (obligatorio)
    @NotNull
    private Long usuarioId;

    // ID del libro que se está prestando (obligatorio)
    @NotNull
    private Long libroId;
}