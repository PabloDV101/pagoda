package com.pagoda.pagoda_api.service.ventas;

import com.pagoda.pagoda_api.entity.ventas.ItemVenta;
import com.pagoda.pagoda_api.entity.ventas.Venta;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.ventas.ItemVentaRepository;
import com.pagoda.pagoda_api.repository.ventas.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemVentaService {

    @Autowired
    private ItemVentaRepository itemRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public List<ItemVenta> listarPorVenta(Integer ventaId) {
        return itemRepository.findByVentaId(ventaId);
    }

    @Transactional
    public ItemVenta agregarItem(ItemVenta item) {
        // 1. Guardamos el item
        ItemVenta guardado = itemRepository.save(item);

        // 2. Actualizamos el total de la venta cabecera
        actualizarTotalVenta(guardado.getVenta().getId());

        return guardado;
    }

    @Transactional
    public void eliminarItem(Integer id) {
        ItemVenta item = itemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.ITEM_NO_ENCONTRADO));

        Integer ventaId = item.getVenta().getId();
        itemRepository.delete(item);

        // Recalculamos el total después de borrar
        actualizarTotalVenta(ventaId);
    }

    private void actualizarTotalVenta(Integer ventaId) {
        Venta venta = ventaRepository.findById(ventaId).get();
        List<ItemVenta> items = itemRepository.findByVentaId(ventaId);

        BigDecimal nuevoTotal = items.stream()
                .map(i -> i.getPrecioUnitario().multiply(new BigDecimal(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotalCuenta(nuevoTotal);
        ventaRepository.save(venta);
    }
}