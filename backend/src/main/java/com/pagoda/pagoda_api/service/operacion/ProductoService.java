package com.pagoda.pagoda_api.service.operacion;

import com.pagoda.pagoda_api.entity.operacion.Producto;
import com.pagoda.pagoda_api.repository.operacion.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void desactivar(Integer id) {
        productoRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            productoRepository.save(p);
        });
    }
}