package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;
import pe.idad.biblioteca.entity.Rol;

// Convierte entre la entidad Rol y sus DTOs (RolRequest y RolResponse).
@Mapper(componentModel = "spring")
public interface RolMapper {

    // Convierte entidad Rol a RolResponse
    RolResponse toResponse(Rol rol);

    // Convierte RolRequest a entidad Rol
    Rol toEntity(RolRequest request);
}