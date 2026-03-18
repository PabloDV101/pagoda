package com.pagoda.pagoda_api.service.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagoda.pagoda_api.model.catalogos.EstadoMesa;
import com.pagoda.pagoda_api.repository.catalogos.EstadoMesaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoMesaService {

    @Autowired
    private EstadoMesaRepository estadoMesaRepository;

    public List<EstadoMesa> listarTodos() {
        return estadoMesaRepository.findAll();
    }

    public Optional<EstadoMesa> buscarPorId(Integer id) {
        return estadoMesaRepository.findById(id);
    }

    public EstadoMesa guardar(EstadoMesa estado) {
        return estadoMesaRepository.save(estado);
    }

    public void eliminar(Integer id) {
        estadoMesaRepository.deleteById(id);
    }
}