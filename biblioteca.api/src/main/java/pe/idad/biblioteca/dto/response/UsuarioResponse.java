package pe.idad.biblioteca.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioResponse {

    //ID del usuario en la base de datos
    private Long id;

    // Nombre del usuario
    private String nombre;

    // Correo electrónico del usuario
    private String email;

    // Lista de roles asignados al usuario (ADMIN, USER)
    private Set<String> roles;
}