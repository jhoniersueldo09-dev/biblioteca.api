package pe.idad.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    // Token JWT generado al iniciar sesión correctamente
    private String token;
}