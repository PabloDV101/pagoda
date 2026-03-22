package com.pagoda.pagoda_api.repository.operacion;

import com.pagoda.pagoda_api.entity.operacion.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    // Método útil para filtrar mesas por su estado (ej: buscar todas las 'Disponibles')
    List<Mesa> findByEstadoId(Integer estadoId);

    boolean existsByNumero(Integer numero);
}