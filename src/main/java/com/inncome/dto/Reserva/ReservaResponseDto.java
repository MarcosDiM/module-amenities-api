package com.inncome.dto.Reserva;

import com.inncome.model.enums.EstadoReserva;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDto {
    private Long id;
    private Long amenitieId;
    private Long usuarioId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double precioFinal;
    private EstadoReserva estado;
    private List<ReservaServicioDto> servicios;
}
