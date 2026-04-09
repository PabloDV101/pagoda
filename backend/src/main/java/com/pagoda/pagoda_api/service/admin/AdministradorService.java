package com.pagoda.pagoda_api.service.admin;

import com.pagoda.pagoda_api.dto.admin.LoginDTO;
import com.pagoda.pagoda_api.dto.admin.LoginResponseDTO;
import com.pagoda.pagoda_api.entity.operacion.Usuario;
import com.pagoda.pagoda_api.entity.catalogos.Rol;
import com.pagoda.pagoda_api.exception.BusinessException;
import com.pagoda.pagoda_api.exception.ErrorCodigo;
import com.pagoda.pagoda_api.repository.operacion.UsuarioRepository;
import com.pagoda.pagoda_api.repository.catalogos.RolRepository;
import com.pagoda.pagoda_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AdministradorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public Usuario obtenerAdmin() {
        Rol rolAdmin = rolRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new BusinessException(ErrorCodigo.ROL_NO_ENCONTRADO));
        
        return usuarioRepository.findByNombreAndRol("admin", rolAdmin)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.USUARIO_NO_ENCONTRADO));
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        String usuario = loginDTO.getUsuario() != null ? loginDTO.getUsuario().trim() : "";
        String contrasena = loginDTO.getContrasena() != null ? loginDTO.getContrasena().trim() : "";

        // Buscar usuario por nombre
        Usuario usuarioEncontrado = usuarioRepository.findByNombre(usuario)
                .orElseThrow(() -> new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS));

        // Validar que sea administrador
        if (!"ADMIN".equals(usuarioEncontrado.getRol().getNombre())) {
            throw new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS);
        }

        // Validar contraseña
        if (usuarioEncontrado.getContrasena() == null) {
            throw new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS);
        }

        String contrasenaEncriptada = encriptarContrasena(contrasena);
        if (!usuarioEncontrado.getContrasena().equals(contrasenaEncriptada)) {
            throw new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS);
        }

        String token = JwtUtil.generateToken(usuarioEncontrado.getId(), usuarioEncontrado.getNombre());

        return LoginResponseDTO.builder()
                .token(token)
                .usuario(usuarioEncontrado.getNombre())
                .id(usuarioEncontrado.getId())
                .activo(usuarioEncontrado.getActivo())
                .build();
    }

    public Usuario actualizar(String nuevoNombre, String contrasenaActual, String contrasenaNueva) {
        Usuario admin = obtenerAdmin();

        // Validar contraseña actual
        if (admin.getContrasena() == null) {
            throw new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS);
        }

        String contrasenaActualEncriptada = encriptarContrasena(contrasenaActual);
        if (!admin.getContrasena().equals(contrasenaActualEncriptada)) {
            throw new BusinessException(ErrorCodigo.CREDENCIALES_INVALIDAS);
        }

        // Actualizar nombre de usuario si cambió
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            if (!nuevoNombre.equals(admin.getNombre())) {
                if (usuarioRepository.existsByNombre(nuevoNombre)) {
                    throw new BusinessException(ErrorCodigo.NOMBRE_USUARIO_DUPLICADO);
                }
            }
            admin.setNombre(nuevoNombre);
        }

        // Actualizar contraseña si se proporcionó
        if (contrasenaNueva != null && !contrasenaNueva.isBlank()) {
            if (contrasenaNueva.length() < 6) {
                throw new BusinessException(ErrorCodigo.JORNADA_CERRADA);
            }
            admin.setContrasena(encriptarContrasena(contrasenaNueva));
        }

        return usuarioRepository.save(admin);
    }

    private String encriptarContrasena(String contrasena) {
        return Base64.getEncoder().encodeToString(contrasena.getBytes(StandardCharsets.UTF_8));
    }
}
