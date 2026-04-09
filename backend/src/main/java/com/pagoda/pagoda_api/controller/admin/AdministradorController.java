package com.pagoda.pagoda_api.controller.admin;

import com.pagoda.pagoda_api.dto.ApiResponse;
import com.pagoda.pagoda_api.dto.admin.ActualizarAdministradorDTO;
import com.pagoda.pagoda_api.dto.admin.LoginDTO;
import com.pagoda.pagoda_api.dto.admin.LoginResponseDTO;
import com.pagoda.pagoda_api.dto.admin.PerfilAdministradorDTO;
import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.service.admin.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO response = administradorService.login(loginDTO);
        return ApiResponse.success("Login exitoso", response);
    }

    @GetMapping("/perfil")
    public ResponseEntity<ApiResponse<PerfilAdministradorDTO>> obtenerPerfil() {
        Usuario admin = administradorService.obtenerAdmin();
        PerfilAdministradorDTO perfil = PerfilAdministradorDTO.builder()
                .id(admin.getId())
                .usuario(admin.getNombre())
                .activo(admin.getActivo())
                .build();
        return ApiResponse.success("Perfil obtenido", perfil);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse<PerfilAdministradorDTO>> actualizar(@RequestBody ActualizarAdministradorDTO dto) {
        Usuario admin = administradorService.actualizar(dto.getUsuario(), dto.getContrasenaActual(), dto.getContrasenaNueva());
        PerfilAdministradorDTO perfil = PerfilAdministradorDTO.builder()
                .id(admin.getId())
                .usuario(admin.getNombre())
                .activo(admin.getActivo())
                .build();
        return ApiResponse.success("Datos actualizados correctamente", perfil);
    }
}
