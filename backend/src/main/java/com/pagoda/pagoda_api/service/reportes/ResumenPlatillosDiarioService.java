package com.pagoda.pagoda_api.service.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenPlatillosDiario;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.reportes.ResumenPlatillosDiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumenPlatillosDiarioService {

    @Autowired
    private ResumenPlatillosDiarioRepository repository;

    public List<ResumenPlatillosDiario> obtenerPorJornada(Integer jornadaId) {
        List<ResumenPlatillosDiario> reportes = repository.findByJornadaId(jornadaId);
        if (reportes.isEmpty()) {
            throw new BusinessException(ErrorCodigo.REPORTE_NO_ENCONTRADO);
        }
        return reportes;
    }

    public ResumenPlatillosDiario guardar(ResumenPlatillosDiario reporte) {
        reporte.setFechaGeneracion(LocalDateTime.now());
        return repository.save(reporte);
    }
}
