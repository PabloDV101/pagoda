package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.dto.operacion.ActualizarUsuarioDTO;
import com.pagoda.pagoda_api.dto.operacion.CrearUsuarioDTO;
import com.pagoda.pagoda_api.dto.operacion.ListarUsuarioDTO;
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
    public ResponseEntity<ApiResponse<List<ListarUsuarioDTO>>> listar() {
        return ApiResponse.success("Lista de usuarios obtenida", usuarioService.listarTodosDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarUsuarioDTO>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Usuario encontrado", usuarioService.buscarPorIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ListarUsuarioDTO>> crear(@RequestBody CrearUsuarioDTO dto) {
        ListarUsuarioDTO guardado = usuarioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado con éxito", guardado).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarUsuarioDTO>> actualizar(@PathVariable Integer id, @RequestBody ActualizarUsuarioDTO dto) {
        ListarUsuarioDTO actualizado = usuarioService.actualizar(id, dto);
        return ApiResponse.success("Usuario actualizado correctamente", actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ApiResponse.success("Usuario eliminado correctamente", null);
    }
}