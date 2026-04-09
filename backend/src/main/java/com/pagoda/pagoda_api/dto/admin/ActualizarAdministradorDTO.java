package com.pagoda.pagoda_api.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAdministradorDTO {
    private String usuario;
    private String contrasenaActual;
    private String contrasenaNueva;
}
