package com.pagoda.pagoda_api.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String usuario;
    private String contrasena;
}
