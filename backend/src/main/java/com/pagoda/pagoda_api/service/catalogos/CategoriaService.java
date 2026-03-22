package com.pagoda.pagoda_api.service.catalogos;

import java.util.List;
import java.util.Optional;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagoda.pagoda_api.entity.catalogos.Categoria;
import com.pagoda.pagoda_api.repository.catalogos.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.CATEGORIA_NO_ENCONTRADA));
    }

    public Categoria guardar(Categoria categoria) {
        // Si es nueva (ID nulo), validamos que el nombre no esté repetido
        if (categoria.getId() == null && categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_CATEGORIA_DUPLICADO);
        }
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {
        Categoria categoria = buscarPorId(id); // Lanza error si no existe

        try {
            categoriaRepository.delete(categoria);
            categoriaRepository.flush(); // Obligamos a JPA a ejecutar el DELETE ahora
        } catch (Exception e) {
            // Si hay productos usando esta categoría, saltará el error de llave foránea
            throw new BusinessException(ErrorCodigo.CATEGORIA_EN_USO);
        }
    }
}
