package com.pagoda.pagoda_api.repository.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenVentasDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResumenVentasDiarioRepository extends JpaRepository<ResumenVentasDiario, Integer> {
    // Para ver el resumen de una jornada específica
    List<ResumenVentasDiario> findByJornadaId(Integer jornadaId);

    // Para buscar el resumen de una venta particular si fuera necesario
    Optional<ResumenVentasDiario> findByVentaId(Integer ventaId);
}