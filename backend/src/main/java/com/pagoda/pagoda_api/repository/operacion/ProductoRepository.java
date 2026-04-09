package com.pagoda.pagoda_api.repository.operacion;

import com.pagoda.pagoda_api.entity.operacion.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Para mostrar el menú al cliente (solo lo que está a la venta)
    @Query("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.categoria WHERE p.activo = true")
    List<Producto> findByActivoTrue();

    // Para filtrar por categoría (ej: "Solo Bebidas")
    List<Producto> findByCategoriaIdAndActivoTrue(Integer categoriaId);

    boolean existsByNombre(String nombre);
    
    // Buscar por ID con eager loading de categoría
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria WHERE p.id = :id")
    Optional<Producto> findByIdWithCategoria(@Param("id") Integer id);
}