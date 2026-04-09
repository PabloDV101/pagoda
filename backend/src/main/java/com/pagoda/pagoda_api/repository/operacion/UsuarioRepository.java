package com.pagoda.pagoda_api.repository.operacion;

import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Útil para validar que no existan nombres duplicados o para el login
    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByNombreAndRol(String nombre, Rol rol);

    boolean existsByNombre(String nombre);
}