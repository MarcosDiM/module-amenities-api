package com.inncome.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reserva_servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaServicio extends Base {

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(name = "valor_servicio")
    private Double valorServicio;
}
