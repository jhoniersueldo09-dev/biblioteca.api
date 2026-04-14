package pe.idad.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idad.biblioteca.entity.Libro;

// Repositorio de acceso a datos para la entidad Libro.
// Extiende JpaRepository para obtener operaciones CRUD automáticamente
// como guardar, listar, buscar por ID y eliminar sin necesidad de implementación manual.
public interface LibroRepository extends JpaRepository<Libro, Long> {
}