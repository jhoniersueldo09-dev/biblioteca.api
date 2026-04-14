package pe.idad.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.mapper.RolMapper;
import pe.idad.biblioteca.service.RolService;

import java.util.List;
import java.util.stream.Collectors;

// Controlador para gestionar los roles del sistema
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    // Servicio para lógica de negocio y mapper para convertir DTOs
    private final RolService rolService;
    private final RolMapper rolMapper;

    // Lista todos los roles (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolResponse>> listar() {
        List<RolResponse> lista = rolService.listar()
                .stream()
                .map(rolMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    // Busca un rol por ID (solo ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolResponse> buscarPorId(@PathVariable Long id) {
        return rolService.buscarPorId(id)
                .map(rolMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registra un nuevo rol
    @PostMapping
    public ResponseEntity<RolResponse> guardar( @RequestBody RolRequest request) {

        // Convierte request a entidad
        Rol rol = rolMapper.toEntity(request);
        // Guarda el rol en la base de datos
        Rol guardado = rolService.guardar(rol);

        // Retorna el rol guardado como respuesta
        return ResponseEntity.ok(rolMapper.toResponse(guardado));
    }

    // Elimina un rol por ID (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}