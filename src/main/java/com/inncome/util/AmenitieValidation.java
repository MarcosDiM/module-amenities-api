package com.inncome.util;

import com.inncome.dto.Amenitie.AmenitieRequestDto;
import com.inncome.dto.Amenitie.AmenitieDto;
import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.model.DiaHorario;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AmenitieValidation {

    public void validarDiasHorariosParaCreacion(AmenitieRequestDto amenitieRequestDto) {
        if (amenitieRequestDto.getDiasHorarios() == null || amenitieRequestDto.getDiasHorarios().isEmpty()) {
            throw new RuntimeException("Debe configurar al menos un día y horario para el amenitie.");
        }
        Set<String> combinacionesDiasHorarios = new HashSet<>();
        for (DiaHorarioRequestDto dhDto : amenitieRequestDto.getDiasHorarios()) {
            String combinacion = dhDto.getDiaSemana() + "-" + dhDto.getHoraInicio() + "-" + dhDto.getHoraFin();
            if (!combinacionesDiasHorarios.add(combinacion)) {
                throw new RuntimeException("Hay horarios repetidos para el día " + dhDto.getDiaSemana());
            }
        }
    }

    public void validarDiasHorarios(AmenitieDto amenitieDto) {

        // Validar que haya al menos un día y horario
        if (amenitieDto.getDiasHorarios() == null || amenitieDto.getDiasHorarios().isEmpty()) {
            throw new RuntimeException("Debe configurar al menos un día y horario para el amenitie.");
        }

        // Validar combinaciones repetidas
        Set<String> combinacionesDiasHorarios = new HashSet<>();
        for (DiaHorarioRequestDto dhDto : amenitieDto.getDiasHorarios()) {
            String combinacion = dhDto.getDiaSemana() + "-" + dhDto.getHoraInicio() + "-" + dhDto.getHoraFin();
            if (!combinacionesDiasHorarios.add(combinacion)) {
                throw new RuntimeException("Hay horarios repetidos para el día " + dhDto.getDiaSemana());
            }
        }
    }

    public static String generarClave(DiaHorarioRequestDto dto) {
        return dto.getDiaSemana().name() + dto.getHoraInicio().toString() + dto.getHoraFin().toString();
    }

    public static String generarClave(DiaHorario diaHorario) {
        return diaHorario.getDiaSemana().name() + diaHorario.getHoraInicio().toString() + diaHorario.getHoraFin().toString();
    }
}
