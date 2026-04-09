package com.pagoda.pagoda_api.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilAdministradorDTO {
    private Integer id;
    private String usuario;  // Mapeado desde Usuario.nombre
    private Boolean activo;
}
