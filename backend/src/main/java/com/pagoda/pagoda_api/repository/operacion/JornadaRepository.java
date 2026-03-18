package com.pagoda.pagoda_api.repository.operacion;

import com.pagoda.pagoda_api.entity.operacion.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
    // Método clave para saber si hay una jornada abierta actualmente
    Optional<Jornada> findByRealizadaFalse();
}