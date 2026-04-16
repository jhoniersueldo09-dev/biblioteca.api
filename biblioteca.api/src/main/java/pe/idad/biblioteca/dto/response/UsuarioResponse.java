package pe.idad.biblioteca.dto.response;

import lombok.Data;

import java.util.Set;


@Data
public class UsuarioResponse {

    private Long id;

    private String nombre;

    private String email;

    private Set<String> roles;
}