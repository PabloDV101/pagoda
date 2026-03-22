package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.CierreDia;
import com.pagoda.pagoda_api.service.operacion.CierreDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/cierres-dia")
public class CierreDiaController {
    @Autowired private CierreDiaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CierreDia>>> listar() {
        return ApiResponse.success("Historial de cierres obtenido", service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CierreDia>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Cierre encontrado", service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CierreDia>> crear(@RequestBody CierreDia cierre) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cierre generado con éxito", service.guardar(cierre)).getBody());
    }
}