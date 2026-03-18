package com.pagoda.pagoda_api.controller.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.model.catalogos.EstadoMesa;
import com.pagoda.pagoda_api.service.catalogos.EstadoMesaService;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/estados-mesa")
public class EstadoMesaController {

    @Autowired
    private EstadoMesaService estadoMesaService;

    @GetMapping
    public List<EstadoMesa> listar() {
        return estadoMesaService.listarTodos();
    }

    @PostMapping
    public EstadoMesa crear(@RequestBody EstadoMesa estado) {
        return estadoMesaService.guardar(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoMesa> obtenerPorId(@PathVariable Integer id) {
        return estadoMesaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadoMesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
