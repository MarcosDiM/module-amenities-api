package com.inncome.service.serviceImpl;

import com.inncome.dto.Imagen.ImagenRequestDto;
import com.inncome.dto.Imagen.ImagenResponseDto;
import com.inncome.model.Amenitie;
import com.inncome.model.Imagen;
import com.inncome.repository.AmenitieRepository;
import com.inncome.repository.ImagenRepository;
import com.inncome.service.ImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagenServiceImpl implements ImagenService {

    private final ImagenRepository imagenRepository;
    private final AmenitieRepository amenitieRepository;

    @Override
    public ImagenResponseDto agregarImagen(Long amenitieId, ImagenRequestDto dto) {
        Amenitie amenitie = amenitieRepository.findById(amenitieId)
                .orElseThrow();

        Imagen imagen = new Imagen();
        imagen.setUrl(dto.getUrl());
        imagen.setDescripcion(dto.getDescripcion());
        imagen.setAmenitie(amenitie);

        imagen = imagenRepository.save(imagen);
        return new ImagenResponseDto(imagen.getId(), imagen.getUrl(), imagen.getDescripcion());
    }

    @Override
    public void eliminarImagen(Long imagenId) throws ChangeSetPersister.NotFoundException {
        if (!imagenRepository.existsById(imagenId)) {
            throw new ChangeSetPersister.NotFoundException();
        }
        imagenRepository.deleteById(imagenId);
    }

    @Override
    public List<ImagenResponseDto> listarPorAmenitie(Long amenitieId) {
        List<Imagen> imagenes = imagenRepository.findByAmenitieId(amenitieId);
        return imagenes.stream()
                .map(i -> new ImagenResponseDto(i.getId(), i.getUrl(), i.getDescripcion()))
                .collect(Collectors.toList());
    }
}

