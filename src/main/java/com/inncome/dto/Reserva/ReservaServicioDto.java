package com.inncome.dto.Reserva;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservaServicioDto {
    private Long servicioId;
    private Double valorServicio;
}
