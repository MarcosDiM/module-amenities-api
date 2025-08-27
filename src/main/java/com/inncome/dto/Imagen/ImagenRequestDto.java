package com.inncome.dto.Imagen;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenRequestDto {

    @NotNull(message = "La URL de la imagen es obligatoria")
    @NotBlank
    private String url;

    private String descripcion;
}

