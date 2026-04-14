package pe.idad.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;
import pe.idad.biblioteca.mapper.PrestamoMapper;
import pe.idad.biblioteca.service.PrestamoService;

import java.util.List;

// Controlador para gestionar los préstamos de libros
@RestController
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    // Servicio para lógica de negocio y mapper para convertir DTOs
    private final PrestamoService prestamoService;
    private final PrestamoMapper prestamoMapper;

    // Busca todos los préstamos (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PrestamoResponse> listar() {
        return prestamoService.listar()
                .stream()
                .map(prestamoMapper::toResponse)
                .toList();
    }

    // Busca el prestamo por id (solo ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PrestamoResponse obtenerPorId(@PathVariable Long id) {

        return prestamoService.buscarPorId(id)
                .map(prestamoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    }
    // Muestra los préstamos del usuario logueado (solo USER)
    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasRole('USER')")
    public List<PrestamoResponse> misPrestamos(Authentication authentication) {

        String email = authentication.getName();

        return prestamoService.prestamosPorEmail(email)
                .stream()
                .map(prestamoMapper::toResponse)
                .toList();
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
    public PrestamoResponse guardar(@RequestBody PrestamoRequest request) {

        return prestamoMapper.toResponse(
                prestamoService.guardar(request)
        );
    }

    // Permite al usuario devolver un libro (solo USER)
    @PutMapping("/devolver/{id}")
    @PreAuthorize("hasRole('USER')")
    public PrestamoResponse devolver(@PathVariable Long id,
                                     Authentication authentication) {

        // Obtiene el email del usuario autenticado
        String email = authentication.getName();

        // Realiza la devolución y retorna el resultado
        return prestamoMapper.toResponse(
                prestamoService.devolver(id, email)
        );
    }
}