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

    @Autowired
    private TipoCobroService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TipoCobro>>> listar() {
        return ApiResponse.success("Lista de tipos de cobro obtenida", service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TipoCobro>> crear(@RequestBody TipoCobro tipoCobro) {
        TipoCobro guardado = service.guardar(tipoCobro);
        // Usamos HttpStatus.CREATED (201) para ser semánticamente correctos
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tipo de cobro creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoCobro>> obtenerPorId(@PathVariable Integer id) {
        // Usamos Optional (si tu Service lo devuelve) para manejar el error 404
        return service.buscarPorId(id)
                .map(tipoCobro -> ApiResponse.success("Tipo de cobro encontrado", tipoCobro))
                .orElse(ApiResponse.error("No se encontró el tipo de cobro con ID: " + id, HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoCobro>> actualizar(@PathVariable Integer id, @RequestBody TipoCobro detalles) {
        return service.buscarPorId(id)
                .map(metodoExistente -> {
                    // Actualizamos los campos (ej. de "Efectivo" a "Efectivo MXN")
                    metodoExistente.setNombre(detalles.getNombre());
                    // Si tienes campo descripción, también lo actualizamos
                    // metodoExistente.setDescripcion(detalles.getDescripcion());

                    TipoCobro actualizado = service.guardar(metodoExistente);
                    return ApiResponse.success("Tipo de cobro actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El tipo de cobro con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Aquí podrías validar si el rol existe antes de borrar,
        // pero por ahora mantengámoslo simple:
        service.eliminar(id);
        return ApiResponse.success("Tipo de cobro eliminado correctamente", null);
    }
}