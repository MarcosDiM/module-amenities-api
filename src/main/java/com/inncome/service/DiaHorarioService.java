package com.inncome.service;

import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.DiaHorario.DiaHorarioResponseDto;
import com.inncome.model.Amenitie;
import com.inncome.model.DiaHorario;

import java.util.List;

public interface DiaHorarioService {
    DiaHorario findDiaHorarioById(Long id);
    List<DiaHorarioResponseDto> listar();
    DiaHorarioResponseDto actualizar(Long id, DiaHorarioRequestDto dto);
    void eliminar(Long id);

    // Nuevas firmas de métodos que esperan el objeto Amenitie
    DiaHorario agregarDiaHorario(Amenitie amenitie, DiaHorarioRequestDto dhDto);
    DiaHorario eliminarDiaHorario(Amenitie amenitie, Long diaHorarioId);
}