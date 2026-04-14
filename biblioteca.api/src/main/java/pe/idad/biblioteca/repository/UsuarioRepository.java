package pe.idad.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idad.biblioteca.entity.Usuario;

import java.util.Optional;


// Repositorio de acceso a datos para la entidad Usuario.
// Extiende JpaRepository para operaciones CRUD automáticas sobre usuarios.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Consulta personalizada para buscar un usuario por su email.
    // Se usa principalmente en login y autenticación JWT.
    Optional<Usuario> findByEmail(String email);

}