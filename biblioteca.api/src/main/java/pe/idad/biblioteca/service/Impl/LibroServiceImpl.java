package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.entity.Libro;
import pe.idad.biblioteca.repository.LibroRepository;
import pe.idad.biblioteca.service.LibroService;

import java.util.List;
import java.util.Optional;

// Implementación del servicio de libros
// Aquí se maneja la lógica básica CRUD de la entidad Libro
@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {


    // Repositorio para acceder a la base de datos
    private final LibroRepository libroRepository;


    // Obtiene la lista de todos los libros
    @Override
    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    // Guarda un libro (crear o actualizar)
    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }


    // Busca un libro por su ID
    @Override
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    // Elimina un libro por su ID
    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }
}