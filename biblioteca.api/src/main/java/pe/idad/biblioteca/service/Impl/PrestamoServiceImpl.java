package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.dto.response.PrestamoResponse;
import pe.idad.biblioteca.entity.Libro;
import pe.idad.biblioteca.entity.Prestamo;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.mapper.PrestamoMapper;
import pe.idad.biblioteca.repository.LibroRepository;
import pe.idad.biblioteca.repository.PrestamoRepository;
import pe.idad.biblioteca.repository.UsuarioRepository;
import pe.idad.biblioteca.service.PrestamoService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoMapper prestamoMapper;

    // Busca todos los préstamos
    @Override
    public List<PrestamoResponse> listar() {
        return prestamoRepository.findAll()
                .stream()
                .map(prestamoMapper::toResponse)
                .toList();
    }

    // Busca un préstamo por ID
    @Override
    public PrestamoResponse buscarPorId(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        return prestamoMapper.toResponse(prestamo);
    }

    // Registra un nuevo préstamo
    @Override
    public PrestamoResponse guardar(PrestamoRequest request) {

        // 🔥 VALIDACIONES
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("El usuario es obligatorio");
        }

        if (request.getLibroId() == null) {
            throw new RuntimeException("El libro es obligatorio");
        }

        // buscar al usuario por Id
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // buscar al libro por id
        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // valida que el libro este disponible
        if (!libro.isDisponible()) {
            throw new RuntimeException("El libro no está disponible");
        }

        // Crea el préstamo
        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(request.getFechaPrestamo() != null
                        ? request.getFechaPrestamo()
                        : LocalDate.now())
                .fechaDevolucion(request.getFechaDevolucion())
                .build();

        // Marcar libro como no disponible
        libro.setDisponible(false);
        libroRepository.save(libro);

        // Guardar préstamo
        Prestamo guardado = prestamoRepository.save(prestamo);

        return prestamoMapper.toResponse(guardado);
    }

    // Elimina un préstamo
    @Override
    public void eliminar(Long id) {

        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("Préstamo no existe");
        }

        prestamoRepository.deleteById(id);
    }

    // Préstamos del usuario logueado
    @Override
    public List<PrestamoResponse> prestamosPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return prestamoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(prestamoMapper::toResponse)
                .toList();
    }

    // Lógica para devolver un libro
    @Override
    public PrestamoResponse devolver(Long id, String email) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        // validar que el préstamo pertenece al usuario
        if (!prestamo.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("No puedes devolver este libro");
        }

        // validar si ya fue devuelto
        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("El libro ya fue devuelto");
        }

        // Marcar fecha de devolucion
        prestamo.setFechaDevolucion(LocalDate.now());

        // Marca el libro como disponible nuevamente
        Libro libro = prestamo.getLibro();
        libro.setDisponible(true);
        libroRepository.save(libro);

        Prestamo actualizado = prestamoRepository.save(prestamo);

        return prestamoMapper.toResponse(actualizado);
    }
}