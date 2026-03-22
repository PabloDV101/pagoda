package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;

import com.pagoda.pagoda_api.entity.catalogos.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.entity.catalogos.EstadoMesa;
import com.pagoda.pagoda_api.service.catalogos.EstadoMesaService;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/estados-mesa")
public class EstadoMesaController {
    @Autowired private EstadoMesaService service;

    @GetMapping public ResponseEntity<ApiResponse<List<EstadoMesa>>> listar() {
        return ApiResponse.success("Lista obtenida", service.listarTodos());
    }

    @GetMapping("/{id}") public ResponseEntity<ApiResponse<EstadoMesa>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Encontrado", service.buscarPorId(id));
    }

    @PostMapping public ResponseEntity<ApiResponse<EstadoMesa>> crear(@RequestBody EstadoMesa e) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Creado", service.guardar(e)).getBody());
    }

    @PutMapping("/{id}") public ResponseEntity<ApiResponse<EstadoMesa>> actualizar(@PathVariable Integer id, @RequestBody EstadoMesa d) {
        EstadoMesa ex = service.buscarPorId(id);
        ex.setNombre(d.getNombre());
        return ApiResponse.success("Actualizado", service.guardar(ex));
    }

    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ApiResponse.success("Eliminado", null);
    }
}
