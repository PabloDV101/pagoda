package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Jornada;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JornadaService {
    @Autowired private JornadaRepository repository;

    public Jornada abrirJornada(Jornada jornada) {
        // REGLA: Solo una jornada abierta a la vez
        if (repository.findByRealizadaFalse().isPresent()) {
            throw new BusinessException(ErrorCodigo.JORNADA_YA_ABIERTA);
        }
        jornada.setRealizada(false);
        jornada.setHoraApertura(LocalDateTime.now());
        return repository.save(jornada);
    }

    public Jornada cerrarJornada(Integer id) {
        Jornada jornada = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.JORNADA_NO_ENCONTRADA));

        if (jornada.getRealizada()) {
            throw new BusinessException(ErrorCodigo.JORNADA_CERRADA);
        }

        jornada.setRealizada(true);
        jornada.setHoraCierre(LocalDateTime.now());
        return repository.save(jornada);
    }

    public List<Jornada> listarTodas() {
        return repository.findAll();
    }


    public Optional<Jornada> obtenerJornadaAbierta() {
        return repository.findByRealizadaFalse();
    }
}