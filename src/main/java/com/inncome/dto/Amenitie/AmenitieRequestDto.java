package com.inncome.dto.Amenitie;

import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.Imagen.ImagenRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmenitieRequestDto {

    @NotNull(message = "El ID del establecimiento es obligatorio")
    private Long establecimientoId;

    @NotBlank(message = "El nombre del amenitie es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del amenitie es obligatoria")
    private String descripcion;

    private Double precio;

    private Integer capacidadMaxima;

    @NotNull(message = "La duración de la reserva es obligatoria")
    private Integer duracionReserva;

    @NotBlank(message = "La ubicación del amenitie es obligatoria")
    private String ubicacion;

    @NotBlank(message = "El mensaje del amenitie es obligatorio")
    private String mensaje;

    @NotBlank(message = "La URL del reglamento es obligatoria")
    private String urlReglamento;

    @Builder.Default
    private Set<Long> serviciosIds = new HashSet<>();

    @NotNull(message = "Los días y horarios son obligatorios")
    @Builder.Default
    private Set<@Valid DiaHorarioRequestDto> diasHorarios = new HashSet<>();

    @Builder.Default
    private Set<@Valid ImagenRequestDto> imagenes = new HashSet<>();
}
