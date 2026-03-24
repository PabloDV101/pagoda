package com.pagoda.pagoda_api.controller.reportes;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.reportes.ResumenVentasDiario;
import com.pagoda.pagoda_api.service.reportes.ResumenVentasDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/ventas-diarias")
public class ResumenVentasDiarioController {

    @Autowired
    private ResumenVentasDiarioService service;

    @GetMapping("/jornada/{jornadaId}")
    public ResponseEntity<ApiResponse<List<ResumenVentasDiario>>> obtenerPorJornada(@PathVariable Integer jornadaId) {
        return ApiResponse.success("Resumen de ventas diario obtenido", service.listarPorJornada(jornadaId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResumenVentasDiario>> crear(@RequestBody ResumenVentasDiario resumen) {
        return ApiResponse.success("Resumen de venta registrado con éxito", service.guardar(resumen));
    }
}