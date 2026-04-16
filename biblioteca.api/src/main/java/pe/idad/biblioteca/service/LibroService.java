package pe.idad.biblioteca.service;

import pe.idad.biblioteca.dto.request.LibroRequest;
import pe.idad.biblioteca.dto.response.LibroResponse;

import java.util.List;

public interface LibroService {

    List<LibroResponse> listar();

    LibroResponse guardar(LibroRequest request);

    LibroResponse buscarPorId(Long id);

    void eliminar(Long id);
}