package com.pagoda.pagoda_api.service.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pagoda.pagoda_api.entity.catalogos.TipoCobro;

import com.pagoda.pagoda_api.repository.catalogos.TipoCobroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCobroService {

    @Autowired
    private TipoCobroRepository tipoCobroRepository;

    public List<TipoCobro> listarTodos() {
        return tipoCobroRepository.findAll();
    }

    public Optional<TipoCobro> buscarPorId(Integer id) {
        return tipoCobroRepository.findById(id);
    }

    public TipoCobro guardar(TipoCobro tipo) {
        return tipoCobroRepository.save(tipo);
    }

    public void eliminar(Integer id) {
        tipoCobroRepository.deleteById(id);
    }
}