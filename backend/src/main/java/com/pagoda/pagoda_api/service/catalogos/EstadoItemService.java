package com.pagoda.pagoda_api.service.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagoda.pagoda_api.model.catalogos.EstadoItem;

import com.pagoda.pagoda_api.repository.catalogos.EstadoItemRepository;


import java.util.List;
import java.util.Optional;

@Service
public class EstadoItemService {

    @Autowired
    private EstadoItemRepository estadoItemRepository;

    public List<EstadoItem> listarTodos() {
        return estadoItemRepository.findAll();
    }

    public Optional<EstadoItem> buscarPorId(Integer id) {
        return estadoItemRepository.findById(id);
    }

    public EstadoItem guardar(EstadoItem estado) {
        return estadoItemRepository.save(estado);
    }

    public void eliminar(Integer id) {
        estadoItemRepository.deleteById(id);
    }
}