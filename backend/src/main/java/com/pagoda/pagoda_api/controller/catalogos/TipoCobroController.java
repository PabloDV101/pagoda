package com.pagoda.pagoda_api.controller.catalogos;

import com.pagoda.pagoda_api.entity.catalogos.TipoCobro;
import com.pagoda.pagoda_api.service.catalogos.TipoCobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos/tipos-cobro")
public class TipoCobroController {

    @Autowired
    private TipoCobroService service;

    @GetMapping
    public List<TipoCobro> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public TipoCobro crear(@RequestBody TipoCobro objeto) {
        return service.guardar(objeto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCobro> obtenerPorId(@PathVariable Integer id) {
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