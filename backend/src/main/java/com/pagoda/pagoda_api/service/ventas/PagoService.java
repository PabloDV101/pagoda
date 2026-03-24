package com.pagoda.pagoda_api.service.ventas;

import com.pagoda.pagoda_api.entity.ventas.Pago;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.ventas.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarPorVenta(Integer ventaId) {
        return pagoRepository.findByVentaId(ventaId);
    }

    public Pago registrarPago(Pago pago) {
        if (pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCodigo.MONTO_PAGO_INVALIDO);
        }

        // Calcular Neto del Pago: monto - (monto * (comision / 100))
        BigDecimal factorComision = pago.getComisionPorcentaje()
                .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        BigDecimal descuento = pago.getMonto().multiply(factorComision);
        pago.setMontoNeto(pago.getMonto().subtract(descuento));

        // Calcular Neto de la Propina (si aplica el mismo porcentaje)
        if (pago.getPropinaMonto().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal descuentoPropina = pago.getPropinaMonto().multiply(factorComision);
            pago.setPropinaNeto(pago.getPropinaMonto().subtract(descuentoPropina));
        }

        return pagoRepository.save(pago);
    }
}