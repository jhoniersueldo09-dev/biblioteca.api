package pe.idad.biblioteca.dto.request;

import lombok.Data;

// DTO utilizado para el inicio de sesión.
// Recibe las credenciales del usuario (email y password).
@Data
public class LoginRequest {

    // Correo electrónico del usuario
    private String email;

    // Contraseña del usuario
    private String password;
}