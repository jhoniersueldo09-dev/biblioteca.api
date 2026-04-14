package pe.idad.biblioteca.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.idad.biblioteca.entity.Rol;
import pe.idad.biblioteca.repository.RolRepository;
import pe.idad.biblioteca.service.RolService;

import java.util.List;
import java.util.Optional;

// Implementación del servicio de roles
// Se encarga de la lógica CRUD y validaciones básicas
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    // Repositorio para interactuar con la base de datos
    private final RolRepository rolRepository;

    // Obtiene todos los roles registrados
    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    // Busca un rol por su ID
    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    // Guarda un nuevo rol con validación de duplicados
    @Override
    public Rol guardar(Rol rol) {

        // Se valida que no exista otro rol con el mismo nombre
        //(SOLO PUEDES CREAR EL ROL_USER Y ROL_ADMIN PARA QUE PUEDAS HACER TODAS LAS PETICIONES DE LA API)
        if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            throw new RuntimeException("El rol ya existe");
        }

        // Si pasa la validación, se guarda en la base de datos
        return rolRepository.save(rol);
    }

    // Elimina un rol por su ID
    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }
}