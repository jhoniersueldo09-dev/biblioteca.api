package pe.idad.biblioteca.dto.response;

import lombok.Data;

/**
 * DTO para devolver información de un Rol en las respuestas del API.
 */
@Data
public class RolResponse {

    /**
     * ID único del rol en la base de datos.
     */
    private Long id;

    /**
     * Nombre del rol (ejemplo: ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR, etc.).
     */
    private String nombre;
}