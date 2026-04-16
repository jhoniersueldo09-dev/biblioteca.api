package pe.idad.biblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * DTO que recibe la información cuando se crea un nuevo usuario.
 */
@Data
public class UsuarioRequest {

    /**
     * Nombre completo del usuario.
     * Es obligatorio y no puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * Debe tener un formato válido (ej: usuario@ejemplo.com) y no puede estar vacío.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Por favor ingresa un email válido")
    private String email;

    /**
     * Contraseña del usuario.
     * Es obligatoria y debe tener al menos 4 caracteres.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    /**
     * Lista de roles que se le asignarán al usuario (ej: "ROLE_ADMIN", "ROLE_USER").
     * Puede estar vacío si no se quieren asignar roles en el momento de creación.
     */
    private Set<String> roles;
}