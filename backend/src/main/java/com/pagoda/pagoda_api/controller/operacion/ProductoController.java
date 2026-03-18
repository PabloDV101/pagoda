package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.entity.operacion.Producto;
import com.pagoda.pagoda_api.service.operacion.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarActivos(); // Por defecto mostramos solo activos
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto detalles) {
        // Primero nos aseguramos de que el ID del objeto coincida con el de la URL
        detalles.setId(id);
        Producto productoActualizado = productoService.guardar(detalles);

        // Envolvemos el producto en un ResponseEntity con estado 200 OK
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}