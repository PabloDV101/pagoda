package com.pagoda.pagoda_api.service.catalogos;

import java.util.List;
import java.util.Optional;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagoda.pagoda_api.entity.catalogos.Rol;
import com.pagoda.pagoda_api.repository.catalogos.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    public Rol buscarPorId(Integer id) {
        // Si no lo encuentra, lanza la excepción de negocio inmediatamente
        return rolRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.ROL_NO_ENCONTRADO));
    }

    public Rol guardar(Rol rol) {
        // Validar duplicados antes de insertar
        // (Asumiendo que tienes un método findByNombre en tu Repository)
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_ROL_DUPLICADO);
        }
        return rolRepository.save(rol);
    }

    public void eliminar(Integer id) {
        Rol rol = buscarPorId(id); // Si no existe, lanza ROL_NO_ENCONTRADO

        try {
            rolRepository.delete(rol);
            rolRepository.flush(); // Forzamos a que JPA intente el borrado AHORA
        } catch (Exception e) {
            // Si Postgres dice "No puedo porque hay usuarios usándolo", atrapamos el error
            throw new BusinessException(ErrorCodigo.ROL_EN_USO);
        }

    }
}