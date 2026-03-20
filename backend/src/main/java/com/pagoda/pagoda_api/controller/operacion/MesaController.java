package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.Mesa;
import com.pagoda.pagoda_api.service.operacion.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mesa>>> listar() {
        return ApiResponse.success("Lista de mesas obtenida", mesaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Mesa>> obtenerPorId(@PathVariable Integer id) {
        return mesaService.buscarPorId(id)
                .map(m -> ApiResponse.success("Mesa encontrada", m))
                .orElse(ApiResponse.error("No se encontró la mesa con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mesa>> crear(@RequestBody Mesa mesa) {
        Mesa guardada = mesaService.guardar(mesa);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Mesa creada con éxito", guardada).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Mesa>> actualizar(@PathVariable Integer id, @RequestBody Mesa detalles) {
        return mesaService.buscarPorId(id)
                .map(mesaExistente -> {
                    mesaExistente.setNumero(detalles.getNumero());
                    mesaExistente.setCapacidad(detalles.getCapacidad());
                    mesaExistente.setEstado(detalles.getEstado());
                    // Si agregaste el campo activo:
                    mesaExistente.setActivo(detalles.getActivo());

                    Mesa actualizada = mesaService.guardar(mesaExistente);
                    return ApiResponse.success("Mesa actualizada correctamente", actualizada);
                })
                .orElse(ApiResponse.error("No se puede actualizar: La mesa con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        mesaService.desactivar(id);
        return ApiResponse.success("Mesa desactivada correctamente", null);
    }
}