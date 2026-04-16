package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.dto.request.LibroRequest;
import pe.idad.biblioteca.dto.response.LibroResponse;
import pe.idad.biblioteca.entity.Libro;
import pe.idad.biblioteca.mapper.LibroMapper;
import pe.idad.biblioteca.repository.LibroRepository;
import pe.idad.biblioteca.service.LibroService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    @Override
    public List<LibroResponse> listar() {
        return libroRepository.findAll()
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    @Override
    public LibroResponse guardar(LibroRequest request) {

        if (request.getTitulo() == null || request.getTitulo().isEmpty()) {
            throw new RuntimeException("El título es obligatorio");
        }

        if (request.getAutor() == null || request.getAutor().isEmpty()) {
            throw new RuntimeException("El autor es obligatorio");
        }

        Libro libro = libroMapper.toEntity(request);
        libro.setDisponible(true);

        return libroMapper.toResponse(
                libroRepository.save(libro)
        );
    }

    @Override
    public LibroResponse buscarPorId(Long id) {
        return libroRepository.findById(id)
                .map(libroMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no existe");
        }
        libroRepository.deleteById(id);
    }
}