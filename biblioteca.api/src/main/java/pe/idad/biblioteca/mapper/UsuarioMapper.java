package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.entity.Usuario;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", source = "roles")
    Usuario toEntity(UsuarioRequest request);


    @Mapping(target = "roles", expression = "java(mapRoles(usuario.getRoles()))")
    UsuarioResponse toResponse(Usuario usuario);

    default Set<Rol> map(Set<String> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(nombre -> {
                    Rol rol = new Rol();
                    rol.setNombre(nombre);
                    return rol;
                })
                .collect(Collectors.toSet());
    }

    default Set<String> mapRoles(Set<Rol> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());
    }
}