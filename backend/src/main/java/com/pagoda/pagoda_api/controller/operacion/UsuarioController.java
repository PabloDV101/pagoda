package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.service.operacion.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listar() {
        return ApiResponse.success("Lista de usuarios obtenida", usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Usuario encontrado", usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crear(@RequestBody Usuario usuario) {
        Usuario guardado = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado con éxito", guardado).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizar(@PathVariable Integer id, @RequestBody Usuario d) {
        Usuario ex = usuarioService.buscarPorId(id);
        ex.setNombre(d.getNombre());
        ex.setRol(d.getRol());
        if (d.getPinHash() != null) ex.setPinHash(d.getPinHash());
        return ApiResponse.success("Usuario actualizado", usuarioService.guardar(ex));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        usuarioService.desactivar(id);
        return ApiResponse.success("Usuario desactivado correctamente", null);
    }
}