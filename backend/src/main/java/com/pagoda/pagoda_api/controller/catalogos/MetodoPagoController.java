package com.pagoda.pagoda_api.controller.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.model.catalogos.MetodoPago;
import com.pagoda.pagoda_api.service.catalogos.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService service;

    @GetMapping
    public List<MetodoPago> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public MetodoPago crear(@RequestBody MetodoPago objeto) {
        return service.guardar(objeto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
