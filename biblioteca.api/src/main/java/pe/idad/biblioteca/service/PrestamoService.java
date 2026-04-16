package pe.idad.biblioteca.service;

import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;

import java.util.List;

// Interfaz del servicio de préstamos.
// Define las operaciones relacionadas a la gestión de préstamos de libros,
// incluyendo registro, consulta, eliminación y devolución.
public interface PrestamoService {

    // Obtiene la lista completa de préstamos registrados (uso administrativo)
    List<PrestamoResponse> listar();

    // Busca un préstamo por su id
    PrestamoResponse buscarPorId(Long id);

    // Registra un nuevo préstamo a partir de un request
    PrestamoResponse guardar(PrestamoRequest request);

    // Elimina un préstamo según su ID
    void eliminar(Long id);

    // Obtiene los préstamos asociados a un usuario mediante su email
    List<PrestamoResponse> prestamosPorEmail(String email);

    // Permite devolver un libro prestado
    PrestamoResponse devolver(Long id, String email);
}