package pe.idad.biblioteca.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.LibroRequest;
import pe.idad.biblioteca.dto.response.LibroResponse;
import pe.idad.biblioteca.entity.Libro;
import pe.idad.biblioteca.mapper.LibroMapper;
import pe.idad.biblioteca.service.LibroService;

import java.util.List;

// Controlador para gestionar los libros
@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    // Servicio para la lógica de negocio y mapper para convertir DTOs
    private final LibroService libroService;
    private final LibroMapper libroMapper;

    // Lista todos los libros (puede acceder ADMIN y USER)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<LibroResponse> listar() {
        return libroService.listar()
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public LibroResponse obtenerPorId(@PathVariable Long id) {

        return libroService.buscarPorId(id)
                .map(libroMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    // Registra un nuevo libro (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LibroResponse guardar(@RequestBody LibroRequest request) {

        // Convierte el request a entidad
        Libro libro = libroMapper.toEntity(request);
        libro.setDisponible(true); // El libro se registra como disponible

        // Guarda y retorna como respuesta
        return libroMapper.toResponse(
                libroService.guardar(libro)
        );
    }
    // Actualiza un libro existente (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LibroResponse actualizar(@PathVariable Long id,
                                    @RequestBody LibroRequest request) {
        // Busca el libro por id o lanza error si no existe
        Libro libro = libroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        // Actualiza los datos básicos
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());

        return libroMapper.toResponse(
                libroService.guardar(libro)
        );
    }
    // Elimina un libro por id (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}