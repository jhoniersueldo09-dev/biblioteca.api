package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.dto.request.RolRequest;
import pe.idad.biblioteca.dto.response.RolResponse;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.mapper.RolMapper;
import pe.idad.biblioteca.repository.RolRepository;
import pe.idad.biblioteca.service.RolService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;


    @Override
    public List<RolResponse> listar() {
        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<RolResponse> buscarPorId(Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toResponse);
    }


    @Override
    public RolResponse guardar(RolRequest request) {

        // Validación de negocio: No permitir roles duplicados por nombre
        // Nota: Solo se deben crear ROLE_ADMIN y ROLE_USER según requisitos del sistema
        if (rolRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new RuntimeException("El rol con nombre '" + request.getNombre() + "' ya existe");
        }

        // Mapeo de DTO a Entidad
        Rol rol = rolMapper.toEntity(request);

        // Guardar en base de datos
        Rol rolGuardado = rolRepository.save(rol);

        // Retornar como DTO de respuesta
        return rolMapper.toResponse(rolGuardado);
    }


    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }
}