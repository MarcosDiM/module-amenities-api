package com.inncome.dto.Reserva;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ReservaRequestDto {
    private Long amenitieId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private List<ReservaServicioDto> servicios;
}


