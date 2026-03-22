package com.pagoda.pagoda_api.service.operacion;


import com.pagoda.pagoda_api.entity.operacion.CierreDia;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.CierreDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CierreDiaService {
    @Autowired private CierreDiaRepository repository;

    public List<CierreDia> listarTodos() { return repository.findAll(); }

    public CierreDia buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.CIERRE_NO_ENCONTRADO));
    }

    public CierreDia guardar(CierreDia cierre) {
        // Validamos si ya existe un cierre para esa jornada_id
        if (cierre.getId() == null && repository.findByJornadaId(cierre.getJornada().getId()).isPresent()) {
            throw new BusinessException(ErrorCodigo.CIERRE_YA_EXISTE_PARA_JORNADA);
        }
        cierre.setFechaGeneracion(LocalDateTime.now());
        return repository.save(cierre);
    }
}
