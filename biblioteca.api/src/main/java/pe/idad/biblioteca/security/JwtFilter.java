package pe.idad.biblioteca.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro JWT que intercepta cada petición HTTP.
// Se encarga de validar el token y autenticar al usuario en el contexto de Spring Security.
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // Utilidad para manejar el token JWT
    private final JwtUtil jwtUtil;

    // Servicio que carga los datos del usuario desde la base de datos
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene la ruta del endpoint solicitado
        String path = request.getServletPath();

        // Permite el acceso sin token a endpoints públicos
        if (path.startsWith("/auth")
                || path.startsWith("/roles")
                || path.startsWith("/usuarios")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtiene el header Authorization de la petición
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Verifica si el header contiene un token Bearer válido
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // Si existe usuario y aún no está autenticado en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga los datos del usuario desde la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valida el token JWT
            if (jwtUtil.validateToken(token)) {

                // Crea el objeto de autenticación con roles del usuario
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Agrega detalles de la solicitud al contexto de seguridad
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Establece la autenticación en el contexto de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa con el resto de filtros de la cadena
        filterChain.doFilter(request, response);
    }
}