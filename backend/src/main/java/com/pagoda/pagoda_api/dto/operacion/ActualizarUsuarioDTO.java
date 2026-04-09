package com.pagoda.pagoda_api.dto.operacion;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioDTO {
    private String nombre;
    private Integer rolId;
    private String pinHash;
    private String contrasena;
    private Boolean activo;
}
