package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.entity.Rol;

import java.util.Set;
import java.util.stream.Collectors;

// Se encarga de convertir entre Entity (Usuario) y DTOs (Request/Response).
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Convierte UsuarioRequest a entidad Usuario
    // Se ignora el ID porque se genera automáticamente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", source = "roles")
    Usuario toEntity(UsuarioRequest request);

    // Convierte entidad Usuario a UsuarioResponse
    // Se transforma la lista de roles a un formato de Strings
    @Mapping(target = "roles", expression = "java(mapRoles(usuario.getRoles()))")
    UsuarioResponse toResponse(Usuario usuario);

    // Convierte Set<String> a Set<Rol>
    // Se usa al recibir datos desde el request
    default Set<Rol> map(Set<String> roles) {
        if (roles == null) return null;

        return roles.stream().map(nombre -> {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            return rol;
        }).collect(Collectors.toSet());
    }

    // Convierte Set<Rol> a Set<String>
    // Se usa al enviar respuesta al cliente
    default Set<String> mapRoles(Set<Rol> roles) {
        if (roles == null) return null;

        return roles.stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());
    }
}