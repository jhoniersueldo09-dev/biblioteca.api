package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioRequest {

    // Nombre del usuario que se envía en la petición
    // No puede ser nulo ni estar vacío
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Correo del usuario
    // Debe tener formato válido de email y no puede estar vacío
    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    // Contraseña del usuario
    // Es obligatoria y debe tener mínimo 4 caracteres
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener mínimo 4 caracteres")
    private String password;

    private Set<String> roles;
}