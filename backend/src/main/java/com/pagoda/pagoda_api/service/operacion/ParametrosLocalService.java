package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.ParametrosLocal;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.ParametrosLocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParametrosLocalService {
    @Autowired private ParametrosLocalRepository repository;

    public ParametrosLocal obtenerParametros() {
        return repository.findById(1)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.PARAMETROS_NO_CONFIGURADOS));
    }

    public ParametrosLocal guardar(ParametrosLocal parametros) {
        parametros.setId(1); // Forzamos que siempre sea el registro único
        parametros.setFechaActualizacion(LocalDateTime.now());
        return repository.save(parametros);
    }
}