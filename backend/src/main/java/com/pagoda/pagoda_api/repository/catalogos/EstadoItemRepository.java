package com.pagoda.pagoda_api.repository.catalogos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagoda.pagoda_api.entity.catalogos.EstadoItem;

@Repository
public interface EstadoItemRepository extends JpaRepository<EstadoItem, Integer> {
    boolean existsByNombre(String nombre);
}