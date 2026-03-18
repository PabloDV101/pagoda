package com.pagoda.pagoda_api.repository.catalogos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagoda.pagoda_api.entity.catalogos.EstadoMesa;

@Repository
public interface EstadoMesaRepository extends JpaRepository<EstadoMesa, Integer> {
}