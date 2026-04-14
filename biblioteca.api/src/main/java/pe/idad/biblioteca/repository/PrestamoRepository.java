package pe.idad.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idad.biblioteca.entity.Prestamo;

import java.util.List;

// Repositorio de acceso a datos para la entidad Prestamo.
// Permite operaciones CRUD automáticas y consultas personalizadas.
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Consulta personalizada que obtiene todos los préstamos
    // asociados a un usuario específico por su ID.
    List<Prestamo> findByUsuarioId(Long usuarioId);
}