package com.pagoda.pagoda_api.controller.operacion;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.dto.operacion.ProductoCreateDTO;
import com.pagoda.pagoda_api.entity.operacion.Producto;
import com.pagoda.pagoda_api.entity.catalogos.Categoria;
import com.pagoda.pagoda_api.service.operacion.ProductoService;
import com.pagoda.pagoda_api.repository.catalogos.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacion/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        List<Producto> lista = productoService.listarActivos();
        return ApiResponse.success("Lista de productos obtenida", lista);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crear(@RequestBody ProductoCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoria_id())
            .orElseThrow(() -> new com.pagoda.pagoda_api.exception.BusinessException(
                com.pagoda.pagoda_api.exception.ErrorCodigo.CATEGORIA_NO_ENCONTRADA));
        
        Producto producto = Producto.builder()
            .nombre(dto.getNombre())
            .descripcion(dto.getDescripcion())
            .precio(dto.getPrecio())
            .categoria(categoria)
            .activo(true)
            .build();
        
        Producto guardado = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado con éxito", guardado).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtener(@PathVariable Integer id) {
        return ApiResponse.success("Usuario encontrado", productoService.buscarPorId(id));
    }

@PutMapping("/{id}")
public ResponseEntity<ApiResponse<Producto>> actualizar(@PathVariable Integer id, @RequestBody ProductoCreateDTO dto) {
    Producto ex = productoService.buscarPorId(id);
    ex.setNombre(dto.getNombre());
    ex.setPrecio(dto.getPrecio());
    ex.setDescripcion(dto.getDescripcion());
    
    if (dto.getCategoria_id() != null) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoria_id())
            .orElseThrow(() -> new com.pagoda.pagoda_api.exception.BusinessException(
                com.pagoda.pagoda_api.exception.ErrorCodigo.CATEGORIA_NO_ENCONTRADA));
        ex.setCategoria(categoria);
    }
    
    return ApiResponse.success("Producto actualizado", productoService.guardar(ex));
}

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ApiResponse.success("Producto eliminado/desactivado", null);
    }
}