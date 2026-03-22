package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;

import com.pagoda.pagoda_api.entity.catalogos.TipoCobro;
import com.pagoda.pagoda_api.service.catalogos.TipoCobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/tipos-cobro")
public class TipoCobroController {
    @Autowired private TipoCobroService service;

    @GetMapping public ResponseEntity<ApiResponse<List<TipoCobro>>> listar() {
        return ApiResponse.success("Lista obtenida", service.listarTodos());
    }

    @PostMapping public ResponseEntity<ApiResponse<TipoCobro>> crear(@RequestBody TipoCobro e) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Creado", service.guardar(e)).getBody());
    }

    @PutMapping("/{id}") public ResponseEntity<ApiResponse<TipoCobro>> actualizar(@PathVariable Integer id, @RequestBody TipoCobro d) {
        TipoCobro ex = service.buscarPorId(id);
        ex.setNombre(d.getNombre());
        return ApiResponse.success("Actualizado", service.guardar(ex));
    }

    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ApiResponse.success("Eliminado", null);
    }
}