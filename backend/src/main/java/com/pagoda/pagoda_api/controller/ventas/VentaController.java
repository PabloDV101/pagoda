package com.pagoda.pagoda_api.controller.ventas;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.ventas.Venta;
import com.pagoda.pagoda_api.service.ventas.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/activas")
    public ResponseEntity<ApiResponse<List<Venta>>> listarActivas() {
        return ApiResponse.success("Ventas en curso obtenidas", ventaService.listarVentasActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Venta>> obtenerPorId(@PathVariable Integer id) {
        return ApiResponse.success("Detalle de venta obtenido", ventaService.buscarPorId(id));
    }

    @PostMapping("/abrir")
    public ResponseEntity<ApiResponse<Venta>> abrirVenta(@RequestBody Venta venta) {
        Venta nueva = ventaService.abrirVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Venta abierta con éxito", nueva).getBody());
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<ApiResponse<Venta>> cerrarVenta(@PathVariable Integer id) {
        return ApiResponse.success("Venta cerrada y cobrada correctamente", ventaService.cerrarVenta(id));
    }
}