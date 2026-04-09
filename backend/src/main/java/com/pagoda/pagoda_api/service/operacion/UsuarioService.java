package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.dto.operacion.ActualizarUsuarioDTO;
import com.pagoda.pagoda_api.dto.operacion.CrearUsuarioDTO;
import com.pagoda.pagoda_api.dto.operacion.ListarUsuarioDTO;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.catalogos.RolRepository;
import com.pagoda.pagoda_api.repository.operacion.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repository;
    @Autowired private RolRepository rolRepository;

    public List<Usuario> listarTodos() { 
        return repository.findAll(); 
    }

    public List<ListarUsuarioDTO> listarTodosDTO() {
        return repository.findAll().stream()
                .map(u -> ListarUsuarioDTO.builder()
                        .id(u.getId())
                        .nombre(u.getNombre())
                        .rol(u.getRol().getNombre())
                        .activo(u.getActivo())
                        .build())
                .collect(Collectors.toList());
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.USUARIO_NO_ENCONTRADO));
    }

    public ListarUsuarioDTO buscarPorIdDTO(Integer id) {
        Usuario usuario = buscarPorId(id);
        return ListarUsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().getNombre())
                .activo(usuario.getActivo())
                .build();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null && repository.existsByNombre(usuario.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_USUARIO_DUPLICADO);
        }
        return repository.save(usuario);
    }

    public ListarUsuarioDTO crear(CrearUsuarioDTO dto) {
        // Validar que no existe usuario con ese nombre
        if (repository.existsByNombre(dto.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_USUARIO_DUPLICADO);
        }

        // Obtener el rol
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new BusinessException(ErrorCodigo.ROL_NO_ENCONTRADO));

        // Crear usuario
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .rol(rol)
                .pinHash(dto.getPinHash() != null ? dto.getPinHash() : "")
                .contrasena(dto.getContrasena())
                .activo(true)
                .build();

        usuario = repository.save(usuario);

        return ListarUsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().getNombre())
                .activo(usuario.getActivo())
                .build();
    }

    public ListarUsuarioDTO actualizar(Integer id, ActualizarUsuarioDTO dto) {
        Usuario usuario = buscarPorId(id);

        // Validar nombre único (excepto si es el mismo usuario)
        if (!usuario.getNombre().equals(dto.getNombre()) && repository.existsByNombre(dto.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_USUARIO_DUPLICADO);
        }

        // Obtener nuevo rol si cambió
        if (dto.getRolId() != null && !dto.getRolId().equals(usuario.getRol().getId())) {
            Rol rol = rolRepository.findById(dto.getRolId())
                    .orElseThrow(() -> new BusinessException(ErrorCodigo.ROL_NO_ENCONTRADO));
            usuario.setRol(rol);
        }

        // Actualizar campos
        usuario.setNombre(dto.getNombre());
        if (dto.getPinHash() != null) {
            usuario.setPinHash(dto.getPinHash());
        }
        if (dto.getContrasena() != null) {
            usuario.setContrasena(dto.getContrasena());
        }
        if (dto.getActivo() != null) {
            usuario.setActivo(dto.getActivo());
        }

        usuario = repository.save(usuario);

        return ListarUsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().getNombre())
                .activo(usuario.getActivo())
                .build();
    }

    public void desactivar(Integer id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        repository.save(usuario);
    }

    public void eliminar(Integer id) {
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }
}