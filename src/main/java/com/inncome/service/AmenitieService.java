package com.inncome.service;

import com.inncome.dto.Amenitie.AmenitieRequestDto;
import com.inncome.dto.Amenitie.AmenitieDto;
import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.Imagen.ImagenRequestDto;
import com.inncome.model.*;
import com.inncome.repository.AmenitieRepository;
import com.inncome.repository.ServicioRepository;
import com.inncome.service.serviceImpl.DiaHorarioServiceImpl;
import com.inncome.util.AmenitieValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AmenitieService {
    private final AmenitieRepository amenitieRepository;
    private final ServicioRepository servicioRepository;
    private final ServicioService servicioService;
    private final AmenitieValidation amenitieValidation;
    private final ImagenService imagenService;
    private final DiaHorarioServiceImpl diaHorarioService;

    public Amenitie findAmenitieById(Long amenitieId) {
        return amenitieRepository.findById(amenitieId)
                .orElseThrow(() -> new RuntimeException("Amenitie no encontrado con el id: " + amenitieId));
    }

    public List<Amenitie> getAmenitiesByEstablecimiento(Long establecimientoId) {
        return amenitieRepository.findByEstablecimientoIdAndActivo(establecimientoId, true);
    }

    public Amenitie createAmenitie(AmenitieRequestDto amenitieDto) throws ChangeSetPersister.NotFoundException {

        amenitieValidation.validarDiasHorariosParaCreacion(amenitieDto);

        Double precio = Optional.ofNullable(amenitieDto.getPrecio())
                .filter(p -> p > 0)
                .orElse(null);

        Integer capacidadMaxima = Optional.ofNullable(amenitieDto.getCapacidadMaxima())
                .filter(c -> c > 0)
                .orElse(null);

        Set<Servicio> servicios = new HashSet<>();
        if (amenitieDto.getServiciosIds() != null && !amenitieDto.getServiciosIds().isEmpty()) {
            List<Servicio> serviciosEncontrados = servicioRepository.findAllById(amenitieDto.getServiciosIds());
            if (serviciosEncontrados.size() != amenitieDto.getServiciosIds().size()) {
                throw new RuntimeException("Uno o más servicios no existen para los IDs proporcionados: " + amenitieDto.getServiciosIds());
            }
            servicios.addAll(serviciosEncontrados);
        }

        // Crear Amenitie
        Amenitie nuevoAmenitie = Amenitie.builder()
                .establecimientoId(amenitieDto.getEstablecimientoId())
                .nombre(amenitieDto.getNombre())
                .descripcion(amenitieDto.getDescripcion())
                .precio(precio)
                .capacidadMaxima(capacidadMaxima)
                .duracionReserva(amenitieDto.getDuracionReserva())
                .ubicacion(amenitieDto.getUbicacion())
                .mensaje(amenitieDto.getMensaje())
                .urlReglamento(amenitieDto.getUrlReglamento())
                .estado(true)
                .servicios(servicios)
                .build();

        nuevoAmenitie.setActivo(true);

        amenitieRepository.save(nuevoAmenitie);

        Set<DiaHorario> diasHorarios = new HashSet<>();
        for (DiaHorarioRequestDto dhDto : amenitieDto.getDiasHorarios()) {
            DiaHorario diaHorarioCreado = diaHorarioService.agregarDiaHorario(nuevoAmenitie, dhDto);
            diasHorarios.add(diaHorarioCreado);
        }
        nuevoAmenitie.setDiasHorarios(diasHorarios);

        // Imágenes
        for (ImagenRequestDto imgDto : amenitieDto.getImagenes()) {
            imagenService.agregarImagen(nuevoAmenitie.getId(), imgDto);
        }
        return nuevoAmenitie;
    }

    public Amenitie editAmenitie(AmenitieDto amenitieDto) {

        Amenitie amenitieExistente = findAmenitieById(amenitieDto.getId());

        amenitieValidation.validarDiasHorarios(amenitieDto);

        Double precio = Optional.ofNullable(amenitieDto.getPrecio())
                .filter(p -> p > 0)
                .orElse(null);

        Integer capacidadMaxima = Optional.ofNullable(amenitieDto.getCapacidadMaxima())
                .filter(c -> c > 0)
                .orElse(null);


        amenitieExistente.setNombre(amenitieDto.getNombre());
        amenitieExistente.setDescripcion(amenitieDto.getDescripcion());
        amenitieExistente.setPrecio(precio);
        amenitieExistente.setCapacidadMaxima(capacidadMaxima);
        amenitieExistente.setUbicacion(amenitieDto.getUbicacion());
        amenitieExistente.setMensaje(amenitieDto.getMensaje());
        amenitieExistente.setUrlReglamento(amenitieDto.getUrlReglamento());

        amenitieExistente.getDiasHorarios().clear();
        for (DiaHorarioRequestDto dhDto : amenitieDto.getDiasHorarios()) {
            if (dhDto.getId() != null) {

                DiaHorario diaHorario = diaHorarioService.findDiaHorarioById(dhDto.getId());

                boolean cambio =
                        !diaHorario.getDiaSemana().equals(dhDto.getDiaSemana()) ||
                                !diaHorario.getHoraInicio().equals(dhDto.getHoraInicio()) ||
                                !diaHorario.getHoraFin().equals(dhDto.getHoraFin());

                if (cambio) {
                    diaHorarioService.actualizar(dhDto.getId(), dhDto);
                }
                amenitieExistente.getDiasHorarios().add(diaHorario);
            } else {
                DiaHorario nuevoDiaHorario = diaHorarioService.agregarDiaHorario(amenitieExistente, dhDto);
                amenitieExistente.getDiasHorarios().add(nuevoDiaHorario);
            }
        }

        return amenitieRepository.save(amenitieExistente);
    }

    public void changeStatusAmenitie(Long amenitieId, boolean estado) {
        Amenitie amenitie = findAmenitieById(amenitieId);
        amenitie.setEstado(estado);
        amenitieRepository.save(amenitie);
    }

    // Agrega un servicio existente a un amenitie
    public Amenitie agregarServicioAAmenitie(Long amenitieId, Long servicioId) {
        Amenitie amenitie = findAmenitieById(amenitieId);
        Servicio servicio = servicioService.findServicioById(servicioId);
        amenitie.getServicios().add(servicio);
        return amenitieRepository.save(amenitie);
    }

    // Quita un servicio de un amenitie (no lo elimina de la base)
    public Amenitie quitarServicioDeAmenitie(Long amenitieId, Long servicioId) {
        Amenitie amenitie = findAmenitieById(amenitieId);
        amenitie.getServicios().removeIf(servicio -> servicio.getId().equals(servicioId));
        amenitieRepository.save(amenitie);
        return amenitie;
    }
}