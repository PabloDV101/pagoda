package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.service.operacion.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario detalles) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    usuario.setNombre(detalles.getNombre());
                    usuario.setRol(detalles.getRol());
                    usuario.setPinHash(detalles.getPinHash());
                    usuario.setActivo(detalles.getActivo());
                    return ResponseEntity.ok(usuarioService.guardar(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        usuarioService.eliminarLogica(id);
        return ResponseEntity.noContent().build();
    }
}