package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.entity.catalogos.MetodoPago;
import com.pagoda.pagoda_api.service.catalogos.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService service;


    @GetMapping
    public ResponseEntity<ApiResponse<List<MetodoPago>>> listar() {
        return ApiResponse.success("Lista de metodos de pago obtenida", service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MetodoPago>> crear(@RequestBody MetodoPago metodoPago) {
        MetodoPago guardado = service.guardar(metodoPago);
        // Usamos HttpStatus.CREATED (201) para ser semánticamente correctos
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Metodo de pago creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MetodoPago>> obtenerPorId(@PathVariable Integer id) {
        // Usamos Optional (si tu Service lo devuelve) para manejar el error 404
        return service.buscarPorId(id)
                .map(metodoPago -> ApiResponse.success("Metodo de pago encontrado", metodoPago))
                .orElse(ApiResponse.error("No se encontró el metodo de pago con ID: " + id, HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MetodoPago>> actualizar(@PathVariable Integer id, @RequestBody MetodoPago detalles) {
        return service.buscarPorId(id)
                .map(metodoExistente -> {
                    // Actualizamos los campos (ej. de "Efectivo" a "Efectivo MXN")
                    metodoExistente.setNombre(detalles.getNombre());
                    // Si tienes campo descripción, también lo actualizamos
                    // metodoExistente.setDescripcion(detalles.getDescripcion());

                    MetodoPago actualizado = service.guardar(metodoExistente);
                    return ApiResponse.success("Método de pago actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El método de pago con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {

        service.eliminar(id);
        return ApiResponse.success("Metodo de pago eliminado correctamente", null);
    }
}
