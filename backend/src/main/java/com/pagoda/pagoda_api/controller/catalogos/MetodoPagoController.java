package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.entity.catalogos.MetodoPago;
import com.pagoda.pagoda_api.service.catalogos.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/metodos-pago")
public class MetodoPagoController {
    @Autowired private MetodoPagoService service;

    @GetMapping public ResponseEntity<ApiResponse<List<MetodoPago>>> listar() {
        return ApiResponse.success("Lista obtenida", service.listarTodos());
    }

    @PostMapping public ResponseEntity<ApiResponse<MetodoPago>> crear(@RequestBody MetodoPago e) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Creado", service.guardar(e)).getBody());
    }

    @PutMapping("/{id}") public ResponseEntity<ApiResponse<MetodoPago>> actualizar(@PathVariable Integer id, @RequestBody MetodoPago d) {
        MetodoPago ex = service.buscarPorId(id);
        ex.setNombre(d.getNombre());
        return ApiResponse.success("Actualizado", service.guardar(ex));
    }

    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ApiResponse.success("Eliminado", null);
    }
}
