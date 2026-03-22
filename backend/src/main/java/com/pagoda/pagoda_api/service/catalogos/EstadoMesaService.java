package com.pagoda.pagoda_api.service.catalogos;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagoda.pagoda_api.entity.catalogos.EstadoMesa;
import com.pagoda.pagoda_api.repository.catalogos.EstadoMesaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoMesaService {
    @Autowired private EstadoMesaRepository repository;

    public List<EstadoMesa> listarTodos() { return repository.findAll(); }

    public EstadoMesa buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.ESTADO_MESA_NO_ENCONTRADO));
    }

    public EstadoMesa guardar(EstadoMesa entidad) {
        if (entidad.getId() == null && repository.existsByNombre(entidad.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_ESTADO_MESA_DUPLICADO);
        }
        return repository.save(entidad);
    }

    public void eliminar(Integer id) {
        EstadoMesa entidad = buscarPorId(id);
        try {
            repository.delete(entidad);
            repository.flush();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodigo.ESTADO_MESA_EN_USO);
        }
    }
}