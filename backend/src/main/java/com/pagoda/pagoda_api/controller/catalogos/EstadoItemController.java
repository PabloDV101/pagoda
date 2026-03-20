package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.catalogos.EstadoItem;

import com.pagoda.pagoda_api.service.catalogos.EstadoItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/estados-item")

public class EstadoItemController {

    @Autowired
    private EstadoItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstadoItem>>> listar() {
        return ApiResponse.success("Lista de estados de item obtenida", service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstadoItem>> crear(@RequestBody EstadoItem estadoItem) {
        EstadoItem guardado = service.guardar(estadoItem);
        // Usamos HttpStatus.CREATED (201) para ser semánticamente correctos
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Estado de item creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoItem>> obtenerPorId(@PathVariable Integer id) {
        // Usamos Optional (si tu Service lo devuelve) para manejar el error 404
        return service.buscarPorId(id)
                .map(estadoItem -> ApiResponse.success("Estados de item encontrado", estadoItem))
                .orElse(ApiResponse.error("No se encontró el estado de item con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoItem>> actualizar(@PathVariable Integer id, @RequestBody EstadoItem detalles) {
        return service.buscarPorId(id)
                .map(estadoItem -> {

                    estadoItem.setNombre(detalles.getNombre());


                    EstadoItem actualizado = service.guardar(estadoItem);
                    return ApiResponse.success("Estado de item  actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El estado de item con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Aquí podrías validar si el rol existe antes de borrar,
        // pero por ahora mantengámoslo simple:
        service.eliminar(id);
        return ApiResponse.success("Estado de item eliminado correctamente", null);
    }
}