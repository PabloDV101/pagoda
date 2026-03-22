package com.pagoda.pagoda_api.service.catalogos;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pagoda.pagoda_api.entity.catalogos.MetodoPago;

import com.pagoda.pagoda_api.repository.catalogos.MetodoPagoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {
    @Autowired private MetodoPagoRepository repository;

    public List<MetodoPago> listarTodos() { return repository.findAll(); }

    public MetodoPago buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.METODO_PAGO_NO_ENCONTRADO));
    }

    public MetodoPago guardar(MetodoPago entidad) {
        if (entidad.getId() == null && repository.existsByNombre(entidad.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_METODO_PAGO_DUPLICADO);
        }
        return repository.save(entidad);
    }

    public void eliminar(Integer id) {
        MetodoPago entidad = buscarPorId(id);
        try {
            repository.delete(entidad);
            repository.flush();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodigo.METODO_PAGO_EN_USO);
        }
    }
}