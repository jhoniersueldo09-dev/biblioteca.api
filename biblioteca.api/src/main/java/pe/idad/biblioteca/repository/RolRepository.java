package pe.idad.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idad.biblioteca.entity.Rol;

import java.util.Optional;

// Repositorio de acceso a datos para la entidad Rol.
// Permite operaciones CRUD automáticas y consultas personalizadas.
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Consulta personalizada para buscar un rol por su nombre
    // Se usa para validar roles existentes y asignarlos a usuarios
    Optional<Rol> findByNombre(String nombre);
}