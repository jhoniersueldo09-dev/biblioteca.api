package pe.idad.biblioteca.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.LibroRequest;
import pe.idad.biblioteca.dto.response.LibroResponse;
import pe.idad.biblioteca.service.LibroService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<LibroResponse> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public LibroResponse obtenerPorId(@PathVariable Long id) {
        return libroService.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LibroResponse guardar(@Valid @RequestBody LibroRequest request) {
        return libroService.guardar(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}