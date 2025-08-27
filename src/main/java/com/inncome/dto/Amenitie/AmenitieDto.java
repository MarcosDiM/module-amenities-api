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
public class AmenitieDto {

    @NotNull(message = "El ID del amenitie es obligatorio para la actualización")
    private Long id;

    @NotNull(message = "El ID del establecimiento es obligatorio")
    private Long establecimientoId;

    @NotBlank(message = "El nombre del amenitie es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del amenitie es obligatoria")
    private String descripcion;

    /* ESTE CAMPO ES OPCIONAL */
    private Double precio;

    /* ESTE CAMPO ES OPCIONAL */
    private Integer capacidadMaxima;

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
