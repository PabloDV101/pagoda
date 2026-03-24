package com.pagoda.pagoda_api.controller.ventas;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.ventas.Pago;
import com.pagoda.pagoda_api.service.ventas.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<ApiResponse<List<Pago>>> listarPorVenta(@PathVariable Integer ventaId) {
        return ApiResponse.success("Pagos de la venta obtenidos", pagoService.listarPorVenta(ventaId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> registrar(@RequestBody Pago pago) {
        Pago guardado = pagoService.registrarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pago registrado correctamente", guardado).getBody());
    }
}