package pe.idad.biblioteca.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

// Servicio personalizado de autenticación.
// Se encarga de cargar los datos del usuario desde la base de datos
// para que Spring Security pueda autenticarlo.
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Repositorio para acceder a los usuarios registrados
    private final UsuarioRepository usuarioRepository;

    // Metodo principal que Spring Security utiliza para autenticar
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Busca el usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convierte los roles del usuario en permisos (authorities)
        // que Spring Security utiliza para validar accesos
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());

        // Construye un objeto UserDetails con la información necesaria
        // para el proceso de autenticación
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }
}