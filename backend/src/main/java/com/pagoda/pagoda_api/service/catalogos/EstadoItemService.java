package com.pagoda.pagoda_api.service.catalogos;

import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pagoda.pagoda_api.entity.catalogos.EstadoItem;
import com.pagoda.pagoda_api.repository.catalogos.EstadoItemRepository;


import java.util.List;
import java.util.Optional;

@Service
public class EstadoItemService {

    @Autowired
    private EstadoItemRepository estadoItemRepository;

    public List<EstadoItem> listarTodos() {
        return estadoItemRepository.findAll();
    }

    public EstadoItem buscarPorId(Integer id) {
        return estadoItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.ESTADO_ITEM_NO_ENCONTRADO));
    }

    public EstadoItem guardar(EstadoItem estadoItem) {
        // Validar duplicados antes de insertar
        // (Asumiendo que tienes un método findByNombre en tu Repository)
        if (estadoItemRepository.existsByNombre(estadoItem.getNombre())) {
            throw new BusinessException(ErrorCodigo.NOMBRE_ESTADO_ITEM_DUPLICADO);
        }
        return estadoItemRepository.save(estadoItem);
    }

    public void eliminar(Integer id) {
        EstadoItem estadoItem= buscarPorId(id); // Si no existe, lanza ROL_NO_ENCONTRADO

        try {
            estadoItemRepository.delete(estadoItem);
            estadoItemRepository.flush(); // Forzamos a que JPA intente el borrado AHORA
        } catch (Exception e) {
            // Si Postgres dice "No puedo porque hay usuarios usándolo", atrapamos el error
            throw new BusinessException(ErrorCodigo.ESTADO_ITEM_EN_USO);
        }

    }
}