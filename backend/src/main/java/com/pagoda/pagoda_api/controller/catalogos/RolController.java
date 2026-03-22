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
        // Si el service no lo encuentra, el GlobalExceptionHandler atrapará el error
        Rol rol = rolService.buscarPorId(id);
        return ApiResponse.success("Rol encontrado", rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> actualizar(@PathVariable Integer id, @RequestBody Rol detalles) {
        // El service lanzará ROL_NO_ENCONTRADO si el ID no existe
        Rol existente = rolService.buscarPorId(id);

        existente.setNombre(detalles.getNombre());
        existente.setDescripcion(detalles.getDescripcion());

        Rol actualizado = rolService.guardar(existente);
        return ApiResponse.success("Rol actualizado correctamente", actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Aquí podrías validar si el rol existe antes de borrar,
        // pero por ahora mantengámoslo simple:
        rolService.eliminar(id);
        return ApiResponse.success("Rol eliminado correctamente", null);
    }
}