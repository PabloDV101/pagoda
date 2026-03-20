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
    public ResponseEntity<ApiResponse<Usuario>> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(u -> ApiResponse.success("Usuario encontrado", u))
                .orElse(ApiResponse.error("No se encontró el usuario con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crear(@RequestBody Usuario usuario) {
        Usuario guardado = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado con éxito", guardado).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizar(@PathVariable Integer id, @RequestBody Usuario detalles) {
        return usuarioService.buscarPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(detalles.getNombre());
                    usuarioExistente.setRol(detalles.getRol());
                    usuarioExistente.setActivo(detalles.getActivo());

                    // Solo actualizamos el PIN si el front envía uno nuevo
                    if (detalles.getPinHash() != null && !detalles.getPinHash().isEmpty()) {
                        usuarioExistente.setPinHash(detalles.getPinHash());
                    }

                    Usuario actualizado = usuarioService.guardar(usuarioExistente);
                    return ApiResponse.success("Usuario actualizado correctamente", actualizado);
                })
                .orElse(ApiResponse.error("No se puede actualizar: El usuario con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Hacemos un borrado lógico (desactivar)
        usuarioService.desactivar(id);
        return ApiResponse.success("Usuario desactivado correctamente", null);
    }
}