    package pe.idad.biblioteca.mapper;

    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import pe.idad.biblioteca.dto.request.LibroRequest;
    import pe.idad.biblioteca.dto.response.LibroResponse;
    import pe.idad.biblioteca.entity.Libro;

    // Convierte entre la entidad Libro y sus DTOs (LibroRequest y LibroResponse).
    @Mapper(componentModel = "spring")
    public interface LibroMapper {

        // Convierte LibroRequest a entidad Libro
        // Se ignoran el id y el campo disponible porque se manejan en la lógica del servicio
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "disponible", ignore = true)
        Libro toEntity(LibroRequest request);

        // Convierte entidad Libro a LibroResponse
        LibroResponse toResponse(Libro libro);
    }