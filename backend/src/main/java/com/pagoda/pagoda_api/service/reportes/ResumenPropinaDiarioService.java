package com.pagoda.pagoda_api.service.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenPropinaDiario;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.reportes.ResumenPropinaDiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumenPropinaDiarioService {

    @Autowired
    private ResumenPropinaDiarioRepository repository;

    public List<ResumenPropinaDiario> listarPorJornada(Integer jornadaId) {
        List<ResumenPropinaDiario> reportes = repository.findByJornadaId(jornadaId);
        if (reportes.isEmpty()) {
            throw new BusinessException(ErrorCodigo.REPORTE_NO_ENCONTRADO);
        }
        return reportes;
    }

    public ResumenPropinaDiario guardar(ResumenPropinaDiario resumen) {
        resumen.setFechaGeneracion(LocalDateTime.now());
        // Lógica de seguridad: Aseguramos que el total neto sea la suma de ambos
        resumen.setTotalPropinasNeto(
                resumen.getPropinasEfectivo().add(resumen.getPropinasTarjetaNeto())
        );
        return repository.save(resumen);
    }
}