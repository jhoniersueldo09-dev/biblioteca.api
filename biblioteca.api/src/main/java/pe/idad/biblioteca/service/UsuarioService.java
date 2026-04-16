package pe.idad.biblioteca.service;

import pe.idad.biblioteca.dto.request.UsuarioRequest;
import pe.idad.biblioteca.dto.response.UsuarioResponse;

import java.util.List;


public interface UsuarioService {


    List<UsuarioResponse> listar();


    UsuarioResponse crear(UsuarioRequest request);

}