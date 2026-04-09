package com.pagoda.pagoda_api.dto.operacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCreateDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer categoria_id;
}
