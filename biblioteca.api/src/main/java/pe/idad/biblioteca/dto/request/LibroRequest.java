package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO utilizado para recibir datos al crear o actualizar un Libro.
// Contiene solo los campos necesarios desde el cliente.
@Data
public class LibroRequest {

    // Título del libro (obligatorio)
    @NotBlank
    private String titulo;

    // Autor del libro (obligatorio)
    @NotBlank
    private String autor;
}