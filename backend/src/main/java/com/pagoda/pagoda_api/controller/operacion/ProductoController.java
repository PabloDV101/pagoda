package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.Producto;
import com.pagoda.pagoda_api.service.operacion.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        List<Producto> lista = productoService.listarActivos();
        return ApiResponse.success("Lista de productos obtenida", lista);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crear(@RequestBody Producto producto) {
        Producto guardado = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(@PathVariable Integer id) {
        return productoService.buscarPorId(id)
                .map(p -> ApiResponse.success("Producto encontrado", p))
                .orElse(ApiResponse.error("No se encontró el producto con ID: " + id, HttpStatus.NOT_FOUND));
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<Producto>> actualizar(@PathVariable Integer id, @RequestBody Producto detalles) {
//
//        detalles.setId(id);
//        Producto productoActualizado = productoService.guardar(detalles);
//
//
//        return ApiResponse.success("Creado", productoActualizado);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> actualizar(@PathVariable Integer id, @RequestBody Producto detalles) {
        return productoService.buscarPorId(id)
                .map(existente -> {
                    detalles.setId(id);
                    Producto actualizado = productoService.guardar(detalles);
                    return ApiResponse.success("Producto actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El producto con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        productoService.desactivar(id);
        return ApiResponse.success("Producto desactivado correctamente", null);
    }
}