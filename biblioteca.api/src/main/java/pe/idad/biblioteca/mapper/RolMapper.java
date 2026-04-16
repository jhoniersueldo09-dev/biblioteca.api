package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;
import pe.idad.biblioteca.entity.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolResponse toResponse(Rol rol);

    Rol toEntity(RolRequest request);
}