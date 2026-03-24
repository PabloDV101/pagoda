package com.pagoda.pagoda_api.controller.ventas;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.ventas.ItemVenta;
import com.pagoda.pagoda_api.service.ventas.ItemVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas/items")
public class ItemVentaController {

    @Autowired
    private ItemVentaService itemService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<ApiResponse<List<ItemVenta>>> listarPorVenta(@PathVariable Integer ventaId) {
        return ApiResponse.success("Items de la cuenta obtenidos", itemService.listarPorVenta(ventaId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemVenta>> agregar(@RequestBody ItemVenta item) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto agregado a la cuenta", itemService.agregarItem(item)).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        itemService.eliminarItem(id);
        return ApiResponse.success("Producto removido de la cuenta", null);
    }
}