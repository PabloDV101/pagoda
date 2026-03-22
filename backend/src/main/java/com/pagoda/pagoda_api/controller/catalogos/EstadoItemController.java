package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.catalogos.Categoria;
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
        return ApiResponse.success("Estado de item encontrada", service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoItem>> actualizar(@PathVariable Integer id, @RequestBody EstadoItem detalles) {
        // El service lanza error si no existe
        EstadoItem existente = service.buscarPorId(id);

        existente.setNombre(detalles.getNombre());

        EstadoItem actualizada = service.guardar(existente);
        return ApiResponse.success("Estado de item actualizado correctamente", actualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ApiResponse.success("Estado de item eliminado correctamente", null);
    }
}