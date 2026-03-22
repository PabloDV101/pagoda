package com.pagoda.pagoda_api.repository.catalogos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagoda.pagoda_api.entity.catalogos.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Aquí podrías añadir búsquedas personalizadas en el futuro, por ejemplo:
    // List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombre(String nombre);
}
