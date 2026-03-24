package com.pagoda.pagoda_api.repository.ventas;

import com.pagoda.pagoda_api.entity.ventas.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemVentaRepository extends JpaRepository<ItemVenta, Integer> {
    // Para ver todos los platillos de una sola mesa/cuenta
    List<ItemVenta> findByVentaId(Integer ventaId);
}