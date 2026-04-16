package pe.idad.biblioteca.dto.response;

import lombok.Data;

import java.util.Set;

/**
 * DTO que se devuelve cuando pedimos información de un usuario.
 * Contiene los datos básicos que queremos mostrar al cliente.
 */
@Data
public class UsuarioResponse {

    /**
     * ID único del usuario en la base de datos.
     */
    private Long id;

    /**
     * Nombre completo del usuario.
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Lista de roles que tiene el usuario (por ejemplo: ROLE_ADMIN, ROLE_USER).
     */
    private Set<String> roles;
}