package com.pagoda.pagoda_api.repository.ventas;

import com.pagoda.pagoda_api.entity.ventas.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Para ver qué mesas están consumiendo actualmente
    List<Venta> findByFechaCierreIsNull();

    // Para ver las ventas de una jornada específica
    List<Venta> findByJornadaId(Integer jornadaId);
}