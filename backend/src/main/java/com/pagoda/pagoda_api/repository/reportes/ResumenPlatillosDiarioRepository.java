package com.pagoda.pagoda_api.repository.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenPlatillosDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResumenPlatillosDiarioRepository extends JpaRepository<ResumenPlatillosDiario, Integer> {
    // Para ver el ranking de platos de hoy
    List<ResumenPlatillosDiario> findByJornadaId(Integer jornadaId);

    // Para ver qué tanto se vende un producto específico históricamente
    List<ResumenPlatillosDiario> findByProductoId(Integer productoId);
}