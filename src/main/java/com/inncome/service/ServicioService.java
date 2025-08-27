package com.inncome.service;

import com.inncome.dto.ServicioDto;
import com.inncome.model.Servicio;
import com.inncome.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {
    private final ServicioRepository servicioRepository;

    public Servicio findServicioById(Long servicioId) {
        return servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con el id: " + servicioId));
    }

    public List<Servicio> getAllServicios(){
        return servicioRepository.findAllByActivoTrue();
    }

    public List<Servicio> getServiciosByeEstablecimiento(Long usuarioCreadorId) {
        return servicioRepository.findByUsuarioCreadorIdAndActivoTrue(usuarioCreadorId);
    }

    public Servicio createServicio(ServicioDto nuevoServicio) {
        Servicio servicio = new Servicio();
        servicio.setNombreServicio(nuevoServicio.getNombreServicio());
        servicio.setDescripcion(nuevoServicio.getDescripcion());
        servicio.setObligatoriedad(nuevoServicio.getObligatoriedad());
        servicio.setValor(nuevoServicio.getValor());
        servicio.setActivo(true);
        servicio.setUsuarioCreadorId(nuevoServicio.getUsuarioCreadorId());

        return servicioRepository.save(servicio);
    }

    public Servicio editServicio(Long servicioId, ServicioDto servicioDto) {
        Servicio servicio = findServicioById(servicioId);

        servicio.setNombreServicio(servicioDto.getNombreServicio());
        servicio.setDescripcion(servicioDto.getDescripcion());
        servicio.setObligatoriedad(servicioDto.getObligatoriedad());
        servicio.setValor(servicioDto.getValor());

        return servicioRepository.save(servicio);
    }

    public void deleteServicio(Long servicioId) {
        Servicio servicio = findServicioById(servicioId);
        servicio.setActivo(false);
        servicioRepository.save(servicio);
    }
}
