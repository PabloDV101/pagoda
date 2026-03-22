package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Mesa;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
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

    public Mesa buscarPorId(Integer id) {
        return mesaRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCodigo.MESA_NO_ENCONTRADA));
    }

    public Mesa guardar(Mesa mesa) {
        // Validar que el número de mesa sea único al crear
        if (mesa.getId() == null && mesaRepository.existsByNumero(mesa.getNumero())) {
            throw new BusinessException(ErrorCodigo.NUMERO_MESA_DUPLICADO);
        }
        return mesaRepository.save(mesa);
    }
    public void desactivar(Integer id) {
        Mesa mesa = buscarPorId(id);
        mesa.setActivo(false);
        mesaRepository.save(mesa);
    }


}