package com.pagoda.pagoda_api.controller.reportes;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.reportes.ResumenPlatillosDiario;
import com.pagoda.pagoda_api.service.reportes.ResumenPlatillosDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/platillos-diarios")
public class ResumenPlatillosDiarioController {

    @Autowired
    private ResumenPlatillosDiarioService service;

    @GetMapping("/jornada/{jornadaId}")
    public ResponseEntity<ApiResponse<List<ResumenPlatillosDiario>>> listarPorJornada(@PathVariable Integer jornadaId) {
        return ApiResponse.success("Resumen de ventas por platillo obtenido", service.obtenerPorJornada(jornadaId));
    }

    // Nota: El POST usualmente lo llamaría un proceso interno, pero lo dejamos para pruebas
    @PostMapping
    public ResponseEntity<ApiResponse<ResumenPlatillosDiario>> crear(@RequestBody ResumenPlatillosDiario reporte) {
        return ApiResponse.success("Reporte generado manualmente con éxito", service.guardar(reporte));
    }
}