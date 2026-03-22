package com.pagoda.pagoda_api.repository.operacion;

import com.pagoda.pagoda_api.entity.operacion.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Para mostrar el menú al cliente (solo lo que está a la venta)
    List<Producto> findByActivoTrue();

    // Para filtrar por categoría (ej: "Solo Bebidas")
    List<Producto> findByCategoriaIdAndActivoTrue(Integer categoriaId);

    boolean existsByNombre(String nombre);
}