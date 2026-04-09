package com.pagoda.pagoda_api.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String usuario;
    private Integer id;
    private Boolean activo;
}
