package com.pagoda.pagoda_api.controller.catalogos;

import java.util.List;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
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

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> listar() {
        return ApiResponse.success("Lista de categorías obtenida", categoriaService.listarTodas());
    }

//Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> obtenerPorId(@PathVariable Integer id) {
        // Usamos Optional (si tu Service lo devuelve) para manejar el error 404
        return categoriaService.buscarPorId(id)
                .map(categoria -> ApiResponse.success("Categoría encontrada", categoria))
                .orElse(ApiResponse.error("No se encontró la categoría con ID: " + id, HttpStatus.NOT_FOUND));
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>>crear(@RequestBody Categoria categoria) {
        Categoria guardada = categoriaService.guardar(categoria);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Categoría creada con éxito", guardada).getBody());
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(@PathVariable Integer id, @RequestBody Categoria detalles) {
        return categoriaService.buscarPorId(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNombre(detalles.getNombre());
                    categoriaExistente.setDescripcion(detalles.getDescripcion());

                    Categoria actualizada = categoriaService.guardar(categoriaExistente);

                    return ApiResponse.success("Categoría actualizada correctamente", actualizada);
                })
                .orElse(ApiResponse.error("No se puede actualizar: La categoría con ID " + id + " no existe", HttpStatus.NOT_FOUND));
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        // Aquí podrías validar si el rol existe antes de borrar,
        // pero por ahora mantengámoslo simple:
        categoriaService.eliminar(id);
        return ApiResponse.success("Categoría eliminada correctamente", null);
    }
}
