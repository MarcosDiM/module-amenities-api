package com.inncome.Mapper;

import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.DiaHorario.DiaHorarioResponseDto;
import com.inncome.model.DiaHorario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiaHorarioMapper {

    @Mapping(target = "amenitie", ignore = true)
    DiaHorario toDiaHorario(DiaHorarioRequestDto dto);

    @Mapping(target = "amenitieId", ignore = true)
    DiaHorarioResponseDto toDiaHorarioResponseDto(DiaHorario diaHorario);
}




