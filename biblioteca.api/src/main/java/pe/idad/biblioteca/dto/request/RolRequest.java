package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolRequest {


    @NotBlank(message = "El nombre del rol es obligatorio y no puede estar vacío")
    @Size(min = 3, max = 30,
            message = "El nombre del rol debe tener entre 3 y 30 caracteres")
    private String nombre;
}