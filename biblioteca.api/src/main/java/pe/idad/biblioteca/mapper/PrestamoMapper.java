package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;
import pe.idad.biblioteca.entity.Prestamo;

// Se encarga de convertir entre la entidad Prestamo y sus DTOs.
@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    // Convierte PrestamoRequest a entidad Prestamo
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "libro", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Prestamo toEntity(PrestamoRequest request);

    // Convierte entidad Prestamo a PrestamoResponse
    @Mapping(target = "usuario", expression = "java(mapUsuario(prestamo))")
    @Mapping(target = "libro", expression = "java(mapLibro(prestamo))")
    PrestamoResponse toResponse(Prestamo prestamo);

    // Métodos auxiliares (más limpio y mantenible)
    default String mapUsuario(Prestamo prestamo) {
        return prestamo.getUsuario() != null
                ? prestamo.getUsuario().getNombre()
                : null;
    }

    default String mapLibro(Prestamo prestamo) {
        return prestamo.getLibro() != null
                ? prestamo.getLibro().getTitulo()
                : null;
    }
}