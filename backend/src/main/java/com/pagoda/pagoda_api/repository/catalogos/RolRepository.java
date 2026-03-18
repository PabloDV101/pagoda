package com.pagoda.pagoda_api.repository.catalogos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagoda.pagoda_api.model.catalogos.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}