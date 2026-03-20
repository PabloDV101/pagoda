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

    @Autowired
    private EstadoMesaService estadoMesaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstadoMesa>>> listar() {
        return ApiResponse.success("Lista de Estados de mesa obtenida", estadoMesaService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstadoMesa>> crear(@RequestBody EstadoMesa estadoMesa) {
        EstadoMesa guardado = estadoMesaService.guardar(estadoMesa);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Estado de mesa creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoMesa>> obtenerPorId(@PathVariable Integer id) {

        return estadoMesaService.buscarPorId(id)
                .map(estadoMesa -> ApiResponse.success("Estado de mesa encontrado", estadoMesa))
                .orElse(ApiResponse.error("No se encontró el Estado de mesa con ID: " + id, HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoMesa>> actualizar(@PathVariable Integer id, @RequestBody EstadoMesa detalles) {
        return estadoMesaService.buscarPorId(id)
                .map(estadoExistente -> {
                    estadoExistente.setNombre(detalles.getNombre());

                    EstadoMesa actualizado = estadoMesaService.guardar(estadoExistente);
                    return ApiResponse.success("Estado de mesa actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El Estado de mesa con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {

        estadoMesaService.eliminar(id);
        return ApiResponse.success("Estado de mesa eliminado correctamente", null);
    }
}
