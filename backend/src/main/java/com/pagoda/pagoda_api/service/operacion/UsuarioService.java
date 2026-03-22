package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repository;

    public List<Usuario> listarTodos() { return repository.findAll(); }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.USUARIO_NO_ENCONTRADO));
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null && repository.existsByNombre(usuario.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_USUARIO_DUPLICADO);
        }
        return repository.save(usuario);
    }

    public void desactivar(Integer id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        repository.save(usuario);
    }
}