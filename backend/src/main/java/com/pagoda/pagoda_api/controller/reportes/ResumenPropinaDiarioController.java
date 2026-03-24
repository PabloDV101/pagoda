package com.pagoda.pagoda_api.controller.reportes;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.reportes.ResumenPropinaDiario;
import com.pagoda.pagoda_api.service.reportes.ResumenPropinaDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/propinas-diarias")
public class ResumenPropinaDiarioController {

    @Autowired
    private ResumenPropinaDiarioService service;

    @GetMapping("/jornada/{jornadaId}")
    public ResponseEntity<ApiResponse<List<ResumenPropinaDiario>>> obtenerPorJornada(@PathVariable Integer jornadaId) {
        return ApiResponse.success("Resumen de propinas obtenido", service.listarPorJornada(jornadaId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResumenPropinaDiario>> crear(@RequestBody ResumenPropinaDiario resumen) {
        return ApiResponse.success("Registro de propina guardado", service.guardar(resumen));
    }
}