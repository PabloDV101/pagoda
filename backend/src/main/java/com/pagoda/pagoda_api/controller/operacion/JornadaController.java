package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.entity.operacion.Jornada;
import com.pagoda.pagoda_api.service.operacion.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/jornadas")
public class JornadaController {

    @Autowired
    private JornadaService jornadaService;

    @GetMapping
    public List<Jornada> listar() {
        return jornadaService.listarTodas();
    }

    @PostMapping("/abrir")
    public ResponseEntity<?> abrir(@RequestBody Jornada jornada) {
        try {
            return ResponseEntity.ok(jornadaService.abrirJornada(jornada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cerrar/{id}")
    public ResponseEntity<?> cerrar(@PathVariable Integer id, @RequestParam String tipo) {
        try {
            return ResponseEntity.ok(jornadaService.cerrarJornada(id, tipo, null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
