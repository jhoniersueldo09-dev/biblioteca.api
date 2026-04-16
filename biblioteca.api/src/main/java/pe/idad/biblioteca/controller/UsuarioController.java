package pe.idad.biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;
import pe.idad.biblioteca.service.UsuarioService;

import java.util.List;

/**
 * Controlador para gestionar los usuarios del sistema.
 * Este controlador solo se encarga de recibir las peticiones y devolver respuestas.
 * Toda la lógica de negocio está en el Service.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Devuelve la lista completa de usuarios registrados.
     * Solo los administradores (rol ADMIN) pueden acceder a esta información.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * Recibe los datos del usuario, los valida y devuelve la información del usuario creado.
     */
    @PostMapping
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.crear(request);
    }
}