package pe.idad.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;
import pe.idad.biblioteca.service.PrestamoService;

import java.util.List;

// Controlador para gestionar los préstamos de libros
@RestController
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    // Busca todos los préstamos (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PrestamoResponse> listar() {
        return prestamoService.listar();
    }

    // Busca el préstamo por id (solo ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PrestamoResponse obtenerPorId(@PathVariable Long id) {
        return prestamoService.buscarPorId(id);
    }

    // Muestra los préstamos del usuario logueado (solo USER)
    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasRole('USER')")
    public List<PrestamoResponse> misPrestamos(Authentication authentication) {
        String email = authentication.getName();
        return prestamoService.prestamosPorEmail(email);
    }

    // Elimina un préstamo por id (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }

    // Registra préstamo (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PrestamoResponse guardar(@Valid @RequestBody PrestamoRequest request) {
        return prestamoService.guardar(request);
    }

    // Permite al usuario devolver un libro (solo USER)
    @PutMapping("/devolver/{id}")
    @PreAuthorize("hasRole('USER')")
    public PrestamoResponse devolver(@PathVariable Long id,
                                     Authentication authentication) {

        String email = authentication.getName();
        return prestamoService.devolver(id, email);
    }
}