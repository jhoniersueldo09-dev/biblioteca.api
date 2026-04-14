package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.repository.RolRepository;
import pe.idad.biblioteca.repository.UsuarioRepository;
import pe.idad.biblioteca.service.UsuarioService;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Set;


// Implementación del servicio de usuarios.
// Aquí se maneja la lógica de negocio para registrar usuarios,
// asignar roles y encriptar contraseñas antes de guardarlas.
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    // Repositorios para interactuar con la base de datos
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    // Componente encargado de encriptar contraseñas
    private final PasswordEncoder passwordEncoder;

    // Registra un nuevo usuario en el sistema
    @Override
    public Usuario guardar(Usuario usuario) {

        // Validar que el correo no esté registrado previamente
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Obtener los roles desde la base de datos
        // y validar que existan
        Set<Rol> roles = usuario.getRoles().stream()
                .map(rol -> rolRepository.findByNombre(rol.getNombre())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol.getNombre())))
                .collect(Collectors.toSet());

        // Asignar los roles validados al usuario
        usuario.setRoles(roles);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    // Obtiene la lista de todos los usuarios registrados
    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

}