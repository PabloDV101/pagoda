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
    public ResponseEntity<ApiResponse<Mesa>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Mesa encontrada", mesaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mesa>> crear(@RequestBody Mesa mesa) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Mesa creada", mesaService.guardar(mesa)).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Mesa>> actualizar(@PathVariable Integer id, @RequestBody Mesa detalles) {
        // El service lanza MESA_NO_ENCONTRADA si el ID no existe
        Mesa existente = mesaService.buscarPorId(id);

        existente.setNumero(detalles.getNumero());
        existente.setCapacidad(detalles.getCapacidad());
        existente.setEstado(detalles.getEstado());
        existente.setActivo(detalles.getActivo());

        Mesa actualizada = mesaService.guardar(existente);
        return ApiResponse.success("Mesa actualizada correctamente", actualizada);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        mesaService.desactivar(id);
        return ApiResponse.success("Mesa desactivada correctamente", null);
    }

}