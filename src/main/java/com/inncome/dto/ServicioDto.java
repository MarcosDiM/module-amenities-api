package com.inncome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioDto {

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombreServicio;

    @NotBlank(message = "La descripción del servicio es obligatoria")
    private String descripcion;

    @NotNull(message = "La obligatoriedad del servicio es obligatoria")
    private Boolean obligatoriedad;

    @NotNull(message = "El usuario creador del servicio es obligatorio")
    private Long usuarioCreadorId;

    private Double valor;

}
