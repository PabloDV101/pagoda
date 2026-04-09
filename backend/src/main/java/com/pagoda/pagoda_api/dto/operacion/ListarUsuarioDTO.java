package com.pagoda.pagoda_api.dto.operacion;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListarUsuarioDTO {
    private Integer id;
    private String nombre;
    private String rol;
    private Boolean activo;
}
