package com.pagoda.pagoda_api.service.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pagoda.pagoda_api.entity.catalogos.MetodoPago;

import com.pagoda.pagoda_api.repository.catalogos.MetodoPagoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> listarTodos() {
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> buscarPorId(Integer id) {
        return metodoPagoRepository.findById(id);
    }

    public MetodoPago guardar(MetodoPago metodo) {
        return metodoPagoRepository.save(metodo);
    }

    public void eliminar(Integer id) {
        metodoPagoRepository.deleteById(id);
    }
}