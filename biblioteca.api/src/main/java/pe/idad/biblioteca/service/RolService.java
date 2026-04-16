package pe.idad.biblioteca.service;

import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;


import java.util.List;
import java.util.Optional;

public interface RolService {

    List<RolResponse> listar();

    Optional<RolResponse> buscarPorId(Long id);

    RolResponse guardar(RolRequest request);

    void eliminar(Long id);
}