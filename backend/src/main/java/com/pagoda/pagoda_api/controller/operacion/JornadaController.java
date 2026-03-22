package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.Jornada;
import com.pagoda.pagoda_api.service.operacion.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/jornadas")
public class JornadaController {

    @Autowired
    private JornadaService jornadaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Jornada>>> listar() {
        return ApiResponse.success("Historial de jornadas obtenido", jornadaService.listarTodas());
    }

    @PostMapping("/abrir")
    public ResponseEntity<ApiResponse<Jornada>> abrir(@RequestBody Jornada jornada) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Jornada abierta correctamente", jornadaService.abrirJornada(jornada)).getBody());
    }

    @PutMapping("/cerrar/{id}")
    public ResponseEntity<ApiResponse<Jornada>> cerrar(@PathVariable Integer id) {
        return ApiResponse.success("Jornada cerrada correctamente", jornadaService.cerrarJornada(id));
    }

    @GetMapping("/estado")
    public ResponseEntity<ApiResponse<Jornada>> consultarJornadaActual() {
        // Un endpoint extra muy útil para el Front
        return jornadaService.obtenerJornadaAbierta()
                .map(j -> ApiResponse.success("Hay una jornada activa", j))
                .orElse(ApiResponse.error("No hay ninguna jornada abierta", HttpStatus.NOT_FOUND));
    }
}
