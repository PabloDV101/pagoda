package com.pagoda.pagoda_api.repository.catalogos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagoda.pagoda_api.model.catalogos.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    // Aquí podrías añadir búsquedas personalizadas en el futuro, por ejemplo:
    // List<MetodoPago> findByNombreContainingIgnoreCase(String nombre);
}
