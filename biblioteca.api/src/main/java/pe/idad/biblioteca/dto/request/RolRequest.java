package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolRequest {

    // Campo que representa el nombre del rol que se enviará en la petición
    // No puede estar vacío ni ser null
    @NotBlank(message = "El nombre del rol no puede estar vacío")

    // Limita el tamaño del nombre del rol entre 3 y 30 caracteres
    @Size(min = 3, max = 30, message = "El nombre del rol debe tener entre 3 y 30 caracteres")
    private String nombre;
}