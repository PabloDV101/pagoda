package com.pagoda.pagoda_api.service.operacion;

import aj.org.objectweb.asm.commons.Remapper;
import com.pagoda.pagoda_api.entity.operacion.Producto;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired private ProductoRepository repository;

    public List<Producto> listarActivos() { return repository.findByActivoTrue(); }

    public Producto buscarPorId(Integer id) {
        return repository.findByIdWithCategoria(id).orElseThrow(() -> new BusinessException(ErrorCodigo.PRODUCTO_NO_ENCONTRADO));
    }

    public Producto guardar(Producto producto) {
        if (producto.getId() == null && repository.existsByNombre(producto.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_PRODUCTO_DUPLICADO);
        }
        return repository.save(producto);
    }

    public void eliminar(Integer id) {
        Producto producto = buscarPorId(id);
        try {
            repository.delete(producto);
            repository.flush();
        } catch (Exception e) {
            // Si falla por integridad (ya tiene ventas), lo desactivamos en lugar de borrar
            producto.setActivo(false);
            repository.save(producto);
            throw new BusinessException(ErrorCodigo.PRODUCTO_EN_USO);
            // O podrías no lanzar excepción y solo confirmar la desactivación
        }
    }
}