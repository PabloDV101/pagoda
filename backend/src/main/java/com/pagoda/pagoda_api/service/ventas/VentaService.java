package com.pagoda.pagoda_api.service.ventas;

import com.pagoda.pagoda_api.entity.ventas.Venta;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.ventas.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> listarVentasActivas() {
        return ventaRepository.findByFechaCierreIsNull();
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.VENTA_NO_ENCONTRADA));
    }

    public Venta abrirVenta(Venta venta) {
        venta.setFechaCreacion(LocalDateTime.now());
        venta.setTotalCuenta(java.math.BigDecimal.ZERO);
        Venta guardada = ventaRepository.save(venta);
        return ventaRepository.findById(guardada.getId())
                .orElseThrow(() -> new BusinessException(ErrorCodigo.VENTA_NO_ENCONTRADA));
    }

    public Venta cerrarVenta(Integer id) {
        Venta venta = buscarPorId(id);

        if (venta.getFechaCierre() != null) {
            throw new BusinessException(ErrorCodigo.VENTA_YA_CERRADA);
        }

        venta.setFechaCierre(LocalDateTime.now());
        return ventaRepository.save(venta);
    }
}