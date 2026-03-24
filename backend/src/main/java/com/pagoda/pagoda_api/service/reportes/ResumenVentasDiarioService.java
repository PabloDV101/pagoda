package com.pagoda.pagoda_api.service.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenVentasDiario;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.reportes.ResumenVentasDiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumenVentasDiarioService {

    @Autowired
    private ResumenVentasDiarioRepository repository;

    public List<ResumenVentasDiario> listarPorJornada(Integer jornadaId) {
        List<ResumenVentasDiario> reportes = repository.findByJornadaId(jornadaId);
        if (reportes.isEmpty()) {
            throw new BusinessException(ErrorCodigo.REPORTE_NO_ENCONTRADO);
        }
        return reportes;
    }

    public ResumenVentasDiario guardar(ResumenVentasDiario resumen) {
        resumen.setFechaGeneracion(LocalDateTime.now());
        return repository.save(resumen);
    }
}