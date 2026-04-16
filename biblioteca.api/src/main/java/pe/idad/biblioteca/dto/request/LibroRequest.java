package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO utilizado para recibir datos al crear o actualizar un Libro.
// Contiene solo los campos necesarios desde el cliente.
@Data
public class LibroRequest {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

}