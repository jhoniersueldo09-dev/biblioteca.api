package pe.idad.biblioteca.dto.response;

import lombok.Data;

@Data
public class RolResponse {

    //ID del rol en la base de datos
    private Long id;

    // Nombre del rol (ROLE_ADMIN, ROLE_USER)
    private String nombre;
}