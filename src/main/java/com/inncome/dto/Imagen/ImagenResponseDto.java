package com.inncome.dto.Imagen;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ImagenResponseDto {

    private Long id;
    private String url;
    private String descripcion;
}

