package com.inncome.Mapper;

import com.inncome.dto.Reserva.ReservaResponseDto;
import com.inncome.dto.Reserva.ReservaServicioDto;
import com.inncome.model.Reserva;
import com.inncome.model.ReservaServicio;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "amenitieId", source = "amenitie.id")
    @Mapping(target = "servicios", source = "reservaServicios", qualifiedByName = "mapServicios")
    @BeanMapping(ignoreByDefault = false)
    ReservaResponseDto toDto(Reserva reserva);

    // Método auxiliar para convertir lista de ReservaServicio a lista de ServicioReservaResponseDto
    @Named("mapServicios")
    default List<ReservaServicioDto> mapServicios(List<ReservaServicio> reservaServicios) {
        return reservaServicios.stream().map(rs -> ReservaServicioDto.builder()
                .servicioId(rs.getServicio().getId())
                .valorServicio(rs.getValorServicio())
                .build()
        ).collect(Collectors.toList());
    }

}
