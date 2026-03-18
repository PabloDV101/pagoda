package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Jornada;
import com.pagoda.pagoda_api.repository.operacion.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    public List<Jornada> listarTodas() {
        return jornadaRepository.findAll();
    }

    public Jornada abrirJornada(Jornada jornada) {
        // Validar si ya hay una jornada abierta
        if (jornadaRepository.findByRealizadaFalse().isPresent()) {
            throw new RuntimeException("Ya existe una jornada abierta. Debe cerrarla primero.");
        }
        return jornadaRepository.save(jornada);
    }

    public Jornada cerrarJornada(Integer id, String tipoCierre, Integer usuarioCierreId) {
        return jornadaRepository.findById(id).map(j -> {
            j.setHoraCierre(LocalDateTime.now());
            j.setRealizada(true);
            j.setTipoCierre(tipoCierre);
            // Aquí podrías buscar el usuario y setearlo
            return jornadaRepository.save(j);
        }).orElseThrow(() -> new RuntimeException("Jornada no encontrada"));
    }
}