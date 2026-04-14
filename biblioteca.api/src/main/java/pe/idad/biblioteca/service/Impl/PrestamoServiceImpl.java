package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.dto.request.PrestamoRequest;
import pe.idad.biblioteca.entity.Libro;
import pe.idad.biblioteca.entity.Prestamo;
import pe.idad.biblioteca.entity.Usuario;
import pe.idad.biblioteca.repository.LibroRepository;
import pe.idad.biblioteca.repository.PrestamoRepository;
import pe.idad.biblioteca.repository.UsuarioRepository;
import pe.idad.biblioteca.service.PrestamoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Implementación del servicio de préstamos (aquí está la lógica principal)
@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    // Repositorios para acceder a la base de datos
    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    // Busca todos los préstamos
    @Override
    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    // Busca un préstamo por ID
    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    // Registra un nuevo préstamo
    @Override
    public Prestamo guardar(PrestamoRequest request) {

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

        // Crea el préstamo con los datos recibidos
        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(request.getFechaPrestamo() != null
                        ? request.getFechaPrestamo()
                        : LocalDate.now()) // Si no envían fecha, usa la actual
                .fechaDevolucion(request.getFechaDevolucion())
                .build();

        // Marcar libro como no disponible
        libro.setDisponible(false);
        libroRepository.save(libro);

        // Guardar préstamo
        return prestamoRepository.save(prestamo);
    }

    // Elimina un préstamo
    @Override
    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }

    // Préstamos del usuario logueado
    @Override
    public List<Prestamo> prestamosPorEmail(String email) {

        // Busca el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Retorna sus préstamos
        return prestamoRepository.findByUsuarioId(usuario.getId());
    }

    // Lógica para devolver un libro
    @Override
    public Prestamo devolver(Long id, String email) {

        // Busca el préstamo por ID
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
        // Guarda los cambios del préstamo
        return prestamoRepository.save(prestamo);
    }
}