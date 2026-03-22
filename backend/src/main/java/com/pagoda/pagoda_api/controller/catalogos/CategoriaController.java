package com.pagoda.pagoda_api.controller.catalogos;

import java.util.List;

import com.pagoda.pagoda_api.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagoda.pagoda_api.entity.catalogos.Categoria;
import com.pagoda.pagoda_api.service.catalogos.CategoriaService;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("/api/catalogos/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> listar() {
        return ApiResponse.success("Lista de categorías obtenida", categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> obtenerPorId(@PathVariable Integer id) {
        return ApiResponse.success("Categoría encontrada", categoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>> crear(@RequestBody Categoria categoria) {
        Categoria guardada = categoriaService.guardar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Categoría creada con éxito", guardada).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(@PathVariable Integer id, @RequestBody Categoria detalles) {
        // El service lanza error si no existe
        Categoria existente = categoriaService.buscarPorId(id);

        existente.setNombre(detalles.getNombre());
        existente.setDescripcion(detalles.getDescripcion());

        Categoria actualizada = categoriaService.guardar(existente);
        return ApiResponse.success("Categoría actualizada correctamente", actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ApiResponse.success("Categoría eliminada correctamente", null);
    }
}
