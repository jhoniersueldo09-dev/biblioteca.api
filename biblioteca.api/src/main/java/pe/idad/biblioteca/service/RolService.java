package pe.idad.biblioteca.service;

import pe.idad.biblioteca.entity.Rol;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio de roles.
// Define las operaciones básicas para la gestión de roles dentro del sistema,
// como consulta, registro y eliminación.
public interface RolService {

    // Obtiene la lista completa de roles registrados
    List<Rol> listar();

    // Busca un rol por su identificador único
    // Se utiliza Optional para manejar el caso en que no exista
    Optional<Rol> buscarPorId(Long id);

    // Registra un nuevo rol en el sistema
    Rol guardar(Rol rol);

    // Elimina un rol según su ID
    void eliminar(Long id);
}