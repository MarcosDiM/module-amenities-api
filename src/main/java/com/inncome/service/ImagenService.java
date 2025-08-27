package com.inncome.service;

import com.inncome.dto.Imagen.ImagenRequestDto;
import com.inncome.dto.Imagen.ImagenResponseDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface ImagenService {
    ImagenResponseDto agregarImagen(Long amenitieId, ImagenRequestDto dto) throws ChangeSetPersister.NotFoundException;
    void eliminarImagen(Long imagenId) throws ChangeSetPersister.NotFoundException;
    List<ImagenResponseDto> listarPorAmenitie(Long amenitieId);
}

