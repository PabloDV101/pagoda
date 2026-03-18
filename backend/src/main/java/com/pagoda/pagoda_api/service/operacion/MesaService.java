package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Mesa;
import com.pagoda.pagoda_api.repository.operacion.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> buscarPorId(Integer id) {
        return mesaRepository.findById(id);
    }

    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public void eliminar(Integer id) {
        mesaRepository.deleteById(id);
    }
}