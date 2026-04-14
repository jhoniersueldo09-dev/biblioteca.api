package pe.idad.biblioteca.service;

import pe.idad.biblioteca.entity.Usuario;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio de usuarios.
// Define las operaciones principales para la gestión de usuarios,
// como registro y consulta de información.
public interface UsuarioService {

    // Obtiene la lista de todos los usuarios registrados en el sistema
    List<Usuario> listar();

    // Registra un nuevo usuario
    // Incluye lógica de validación, asignación de roles y encriptación de contraseña
    Usuario guardar(Usuario usuario);


}