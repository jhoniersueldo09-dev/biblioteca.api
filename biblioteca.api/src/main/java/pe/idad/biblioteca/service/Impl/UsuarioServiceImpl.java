package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.mapper.UsuarioMapper;
import pe.idad.biblioteca.repository.RolRepository;
import pe.idad.biblioteca.repository.UsuarioRepository;
import pe.idad.biblioteca.service.UsuarioService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuarios.
 * Aquí está toda la lógica de negocio: validaciones, encriptación de contraseña
 * y asignación de roles.
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * Pasos que hace:
     * 1. Valida que el email no esté registrado
     * 2. Encripta la contraseña
     * 3. Valida y asigna los roles
     * 4. Guarda el usuario y devuelve la información
     */
    @Override
    public UsuarioResponse crear(UsuarioRequest request) {

        // Verificamos que el correo no esté ya registrado
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        // Convertimos el DTO a entidad
        Usuario usuario = usuarioMapper.toEntity(request);

        // Encriptamos la contraseña por seguridad
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Validamos que los roles existan en la base de datos y los asignamos
        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            Set<Rol> rolesValidos = usuario.getRoles().stream()
                    .map(rol -> rolRepository.findByNombre(rol.getNombre())
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol.getNombre())))
                    .collect(Collectors.toSet());

            usuario.setRoles(rolesValidos);
        }

        // Guardamos el usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Devolvemos la información del usuario creado
        return usuarioMapper.toResponse(usuarioGuardado);
    }

    /**
     * Devuelve la lista de todos los usuarios registrados.
     */
    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }
}