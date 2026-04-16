package pe.idad.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;
import pe.idad.biblioteca.service.RolService;

import java.util.List;

/**
 * Controlador para gestionar los roles del sistema.
 * Solo se encarga de recibir peticiones y devolver respuestas.
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    /**
     * Lista todos los roles registrados (solo para ADMIN)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolResponse>> listar() {
        List<RolResponse> lista = rolService.listar();
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un rol por su ID (solo para ADMIN)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolResponse> buscarPorId(@PathVariable Long id) {
        return rolService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Registra un nuevo rol en el sistema
     */
    @PostMapping
    public ResponseEntity<RolResponse> guardar(@Valid @RequestBody RolRequest request) {
        RolResponse response = rolService.guardar(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un rol por su ID (solo para ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}