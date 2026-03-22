package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.ParametrosLocal;
import com.pagoda.pagoda_api.service.operacion.ParametrosLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operacion/parametros")
public class ParametrosLocalController {
    @Autowired private ParametrosLocalService service;

    @GetMapping
    public ResponseEntity<ApiResponse<ParametrosLocal>> obtener() {
        // Si no existen, el service lanzará la excepción PARAMETROS_NO_CONFIGURADOS
        return ApiResponse.success("Configuración obtenida", service.obtenerParametros());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ParametrosLocal>> actualizar(@RequestBody ParametrosLocal parametros) {
        return ApiResponse.success("Configuración actualizada correctamente", service.guardar(parametros));
    }
}
