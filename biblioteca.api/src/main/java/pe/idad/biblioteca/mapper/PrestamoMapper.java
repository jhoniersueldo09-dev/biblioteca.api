package pe.idad.biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;
import pe.idad.biblioteca.entity.Prestamo;

// Se encarga de convertir entre la entidad Prestamo y sus DTOs.
// Maneja conversiones personalizadas para usuario y libro.
@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    // Convierte PrestamoRequest a entidad Prestamo
    // Se ignoran relaciones porque se asignan en el servicio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "libro", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Prestamo toEntity(PrestamoRequest request);

    // Convierte entidad Prestamo a PrestamoResponse
    // Se muestran solo datos relevantes (nombre de usuario y título del libro)
    @Mapping(target = "usuario", expression = "java(prestamo.getUsuario() != null ? prestamo.getUsuario().getNombre() : null)")
    @Mapping(target = "libro", expression = "java(prestamo.getLibro() != null ? prestamo.getLibro().getTitulo() : null)")
    PrestamoResponse toResponse(Prestamo prestamo);
}