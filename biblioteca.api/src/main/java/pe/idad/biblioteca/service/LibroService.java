package pe.idad.biblioteca.service;

import pe.idad.biblioteca.entity.Libro;

import java.util.List;
import java.util.Optional;

// Interfaz del servicio de libros.
// Define las operaciones básicas que se pueden realizar sobre la entidad Libro.
// Sirve como contrato para su implementación en la capa de servicio.
public interface LibroService {

    // Obtiene la lista completa de libros registrados
    List<Libro> listar();

    // Guarda un nuevo libro o actualiza uno existente
    Libro guardar(Libro libro);

    // Busca un libro por su identificador único
    // Retorna un Optional para manejar el caso en que no exista
    Optional<Libro> buscarPorId(Long id);

    // Elimina un libro según su ID
    void eliminar(Long id);
}