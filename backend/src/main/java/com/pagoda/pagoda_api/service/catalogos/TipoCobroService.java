package com.pagoda.pagoda_api.service.catalogos;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pagoda.pagoda_api.entity.catalogos.TipoCobro;

import com.pagoda.pagoda_api.repository.catalogos.TipoCobroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCobroService {
    @Autowired private TipoCobroRepository repository;

    public List<TipoCobro> listarTodos() { return repository.findAll(); }

    public TipoCobro buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.TIPO_COBRO_NO_ENCONTRADO));
    }

    public TipoCobro guardar(TipoCobro entidad) {
        if (entidad.getId() == null && repository.existsByNombre(entidad.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_TIPO_COBRO_DUPLICADO);
        }
        return repository.save(entidad);
    }

    public void eliminar(Integer id) {
        TipoCobro entidad = buscarPorId(id);
        try {
            repository.delete(entidad);
            repository.flush();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodigo.TIPO_COBRO_EN_USO);
        }
    }
}