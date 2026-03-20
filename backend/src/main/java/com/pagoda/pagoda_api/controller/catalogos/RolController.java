package com.pagoda.pagoda_api.controller.catalogos;

import java.util.List;

import com.pagoda.pagoda_api.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagoda.pagoda_api.entity.catalogos.Rol;
import com.pagoda.pagoda_api.service.catalogos.RolService;

@RestController
@RequestMapping("/api/catalogos/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Rol>>> listar() {
        return ApiResponse.success("Lista de roles obtenida", rolService.listarTodos());
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Rol>> crear(@RequestBody Rol rol) {
        Rol guardado = rolService.guardar(rol);
        // Usamos HttpStatus.CREATED (201) para ser semánticamente correctos
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Rol creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> obtenerPorId(@PathVariable Integer id) {
        // Usamos Optional (si tu Service lo devuelve) para manejar el error 404
        return rolService.buscarPorId(id)
                .map(rol -> ApiResponse.success("Rol encontrado", rol))
                .orElse(ApiResponse.error("No se encontró el rol con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> actualizar(@PathVariable Integer id, @RequestBody Rol detalles) {
        return rolService.buscarPorId(id)
                .map(rol -> {

                    rol.setNombre(detalles.getNombre());
                    rol.setDescripcion(detalles.getDescripcion());
                    // Si tienes campo descripción, también lo actualizamos
                    // metodoExistente.setDescripcion(detalles.getDescripcion());

                    Rol actualizado = rolService.guardar(rol);
                    return ApiResponse.success("Rol actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El rol con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Aquí podrías validar si el rol existe antes de borrar,
        // pero por ahora mantengámoslo simple:
        rolService.eliminar(id);
        return ApiResponse.success("Rol eliminado correctamente", null);
    }
}