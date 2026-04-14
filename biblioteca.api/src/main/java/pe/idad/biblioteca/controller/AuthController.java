package pe.idad.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import pe.idad.biblioteca.dto.request.LoginRequest;
import pe.idad.biblioteca.dto.response.LoginResponse;
import pe.idad.biblioteca.security.JwtUtil;

// Controlador para manejar el login de usuarios
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // Se usan para validar usuario y generar el token
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    // Endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        try {
            // Se valida el email y contraseña
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Si todo está bien se genera el token JWT
            String token = jwtUtil.generateToken(request.getEmail());

            // Se devuelve el token al usuario
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (AuthenticationException e) {
            // Si las credenciales están mal, retorna error 401
            return ResponseEntity.status(401)
                    .body(new LoginResponse("Credenciales incorrectas"));
        }
    }
}