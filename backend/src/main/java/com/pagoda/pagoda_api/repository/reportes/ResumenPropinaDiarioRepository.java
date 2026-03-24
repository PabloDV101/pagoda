package com.pagoda.pagoda_api.repository.reportes;

import com.pagoda.pagoda_api.entity.reportes.ResumenPropinaDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResumenPropinaDiarioRepository extends JpaRepository<ResumenPropinaDiario, Integer> {
    // Para ver el desglose de propinas de todo el día
    List<ResumenPropinaDiario> findByJornadaId(Integer jornadaId);
}