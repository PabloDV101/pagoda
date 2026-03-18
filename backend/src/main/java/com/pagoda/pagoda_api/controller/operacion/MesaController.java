package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.entity.operacion.Mesa;
import com.pagoda.pagoda_api.service.operacion.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public List<Mesa> listar() {
        return mesaService.listarTodas();
    }

    @PostMapping
    public Mesa crear(@RequestBody Mesa mesa) {
        return mesaService.guardar(mesa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerPorId(@PathVariable Integer id) {
        return mesaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizar(@PathVariable Integer id, @RequestBody Mesa detalles) {
        return mesaService.buscarPorId(id)
                .map(mesa -> {
                    mesa.setNumero(detalles.getNumero());
                    mesa.setCapacidad(detalles.getCapacidad());
                    mesa.setEstado(detalles.getEstado());
                    return ResponseEntity.ok(mesaService.guardar(mesa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}