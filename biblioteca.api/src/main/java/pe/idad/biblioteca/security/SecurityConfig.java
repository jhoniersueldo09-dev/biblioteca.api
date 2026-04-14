package pe.idad.biblioteca.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


// Configuración principal de seguridad de Spring Security.
// Aquí se definen las reglas de acceso, autenticación JWT y control de roles.
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Servicio que carga usuarios desde la base de datos
    private final CustomUserDetailsService userDetailsService;
    // Filtro JWT que valida el token en cada petición
    private final JwtFilter jwtFilter;

    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Desactiva CSRF porque usamos autenticación con JWT (stateless)
                .csrf(csrf -> csrf.disable())

                // Configura la aplicación como stateless (sin sesiones)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define el servicio de autenticación de usuarios
                .userDetailsService(userDetailsService)

                // Reglas de autorización por endpoints
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/roles/**").permitAll()
                        .requestMatchers("/usuarios/**").permitAll()

                        .requestMatchers("/libros/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/prestamos/**").hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )

                // Agrega el filtro JWT antes del filtro de autenticación de Spring
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Permite obtener el AuthenticationManager para el login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Encoder de contraseñas (BCrypt recomendado por seguridad)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}