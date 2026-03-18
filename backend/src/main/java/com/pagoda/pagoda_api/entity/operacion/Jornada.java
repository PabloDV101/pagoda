package com.pagoda.pagoda_api.entity.operacion;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jornadas", schema = "operacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "fondo_caja", nullable = false, precision = 10, scale = 2)
    private BigDecimal fondoCaja;

    @Column(name = "hora_apertura")
    private LocalDateTime horaApertura;

    @Column(name = "hora_cierre")
    private LocalDateTime horaCierre;

    @Column(name = "tipo_cierre", length = 50)
    private String tipoCierre;

    @Builder.Default
    @Column(nullable = false)
    private Boolean realizada = false;

    @ManyToOne
    @JoinColumn(name = "usuario_apertura_id")
    private Usuario usuarioApertura;

    @ManyToOne
    @JoinColumn(name = "usuario_cierre_id")
    private Usuario usuarioCierre;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) this.fecha = LocalDate.now();
        if (this.horaApertura == null) this.horaApertura = LocalDateTime.now();
    }
}