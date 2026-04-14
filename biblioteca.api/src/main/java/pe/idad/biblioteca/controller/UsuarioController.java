package pe.idad.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;
import pe.idad.biblioteca.mapper.UsuarioMapper;
import pe.idad.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;

import java.util.List;

// Controlador para gestionar los usuarios del sistema
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    // Servicio para lógica de negocio y mapper para convertir DTOs
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    // Lista todos los usuarios (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioResponse> listar() {
        return usuarioService.listar()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }
    // Crea un nuevo usuario
    @PostMapping
    public UsuarioResponse crear(@RequestBody @Valid UsuarioRequest request) {
        return usuarioMapper.toResponse(
                usuarioService.guardar(
                        usuarioMapper.toEntity(request)
                )
        );
    }
}