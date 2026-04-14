package pe.idad.biblioteca.service;

import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.entity.Prestamo;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio de préstamos.
// Define las operaciones relacionadas a la gestión de préstamos de libros,
// incluyendo registro, consulta, eliminación y devolución.
public interface PrestamoService {

    // Obtiene la lista completa de préstamos registrados (uso administrativo)
    List<Prestamo> listar();

    // Busca un préstamo por su id
    // Se utiliza Optional para manejar el caso en que no exista
    Optional<Prestamo> buscarPorId(Long id);

    // Registra un nuevo préstamo a partir de un request
    // Contiene la lógica necesaria para asociar usuario y libro
    Prestamo guardar(PrestamoRequest request);

    // Elimina un préstamo según su ID
    void eliminar(Long id);

    // Obtiene los préstamos asociados a un usuario mediante su email
    // Usado para mostrar los préstamos del usuario autenticado
    List<Prestamo> prestamosPorEmail(String email);

    // Permite devolver un libro prestado
    // Valida que el préstamo pertenezca al usuario y actualiza su estado
    Prestamo devolver(Long id, String email);
}
