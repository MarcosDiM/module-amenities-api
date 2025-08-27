package com.inncome.service;

import com.inncome.dto.Reserva.ReservaRequestDto;
import com.inncome.dto.Reserva.ReservaResponseDto;

import java.util.List;

public interface ReservaService {
    ReservaResponseDto crearReserva(ReservaRequestDto dto, Long usuarioId);
    ReservaResponseDto cancelarReserva(Long id);
    ReservaResponseDto confirmarReserva(Long id);
    List<ReservaResponseDto> obtenerReservasPorUsuario(Long userId);
    List<ReservaResponseDto> obtenerTodasLasReservas();
}

