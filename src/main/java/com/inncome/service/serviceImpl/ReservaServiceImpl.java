package com.inncome.service.serviceImpl;

import com.inncome.Mapper.ReservaMapper;
import com.inncome.dto.Reserva.ReservaRequestDto;
import com.inncome.dto.Reserva.ReservaResponseDto;
import com.inncome.dto.Reserva.ReservaServicioDto;
import com.inncome.exception.BusinessException;
import com.inncome.exception.ResourceNotFoundException;
import com.inncome.model.Amenitie;
import com.inncome.model.Reserva;
import com.inncome.model.ReservaServicio;
import com.inncome.model.Servicio;
import com.inncome.model.enums.EstadoReserva;
import com.inncome.repository.AmenitieRepository;
import com.inncome.repository.ReservaRepository;
import com.inncome.repository.ServicioRepository;
import com.inncome.service.ReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepo;
    private final AmenitieRepository amenitieRepo;
    private final ServicioRepository servicioRepo;
    private final ReservaMapper reservaMapper;

    @Override
    @Transactional
    public ReservaResponseDto crearReserva(ReservaRequestDto dto, Long usuarioId) {
        Amenitie amenitie = amenitieRepo.findById(dto.getAmenitieId())
                .orElseThrow(() -> new ResourceNotFoundException("Amenitie no encontrado con ID: " + dto.getAmenitieId()));

        Integer duracion = amenitie.getDuracionReserva();
        if (duracion == null || duracion <= 0) {
            throw new BusinessException("La duración del amenitie debe ser mayor a cero");
        }

        LocalTime horaFinCalculada = dto.getHoraInicio().plusMinutes(duracion);

        if (!dto.getHoraInicio().isBefore(horaFinCalculada)) {
            throw new BusinessException("La hora de inicio debe ser menor que la hora de fin");
        }

        boolean ocupado = reservaRepo.existsConflictingReservation(
                dto.getAmenitieId(), dto.getFecha(), dto.getHoraInicio(), horaFinCalculada, EstadoReserva.CANCELADA);

        if (ocupado) {
            throw new BusinessException("El amenitie ya está reservado en ese horario");
        }


        Reserva reserva = Reserva.builder()
                .amenitie(amenitie)
                .usuarioId(usuarioId)
                .fecha(dto.getFecha())
                .horaInicio(dto.getHoraInicio())
                .horaFin(horaFinCalculada)
                .fechaCreacion(LocalDateTime.now())
                .estado(EstadoReserva.PENDIENTE)
                .build();

        // 4. Procesar servicios asociados con solo los ids para no traer servicios innecesarios
        List<ReservaServicio> reservaServicios = new ArrayList<>();
        double precioServicios = 0.0;

        if (dto.getServicios() != null && !dto.getServicios().isEmpty()) {
            // Buscar todos los servicios solicitados en una sola consulta
            List<Long> servicioIds = dto.getServicios().stream()
                    .map(ReservaServicioDto::getServicioId)
                    .toList();

            List<Servicio> servicios = servicioRepo.findAllById(servicioIds);

            Map<Long, Servicio> servicioMap = servicios.stream()
                    .collect(Collectors.toMap(Servicio::getId, s -> s));

            // Mapear servicios y armar ReservaServicio
            for (ReservaServicioDto servicioDto : dto.getServicios()) {
                Servicio servicio = Optional.ofNullable(servicioMap.get(servicioDto.getServicioId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado: " + servicioDto.getServicioId()));

                double precio = obtenerPrecioServicio(servicioDto, servicio);

                reservaServicios.add(ReservaServicio.builder()
                        .reserva(reserva)
                        .servicio(servicio)
                        .valorServicio(precio)
                        .build());

                precioServicios += precio;
            }
        }

        reserva.setReservaServicios(reservaServicios);
        double precioAmenitie = Optional.ofNullable(amenitie.getPrecio()).orElse(0.0);
        reserva.setPrecioFinal(precioAmenitie + precioServicios);

        Reserva reservaGuardada = reservaRepo.save(reserva);
        return reservaMapper.toDto(reservaGuardada);
    }


    @Override
    @Transactional
    public ReservaResponseDto cancelarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        reserva.setEstado(EstadoReserva.CANCELADA);
        return reservaMapper.toDto(reservaRepo.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponseDto confirmarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new BusinessException("Solo las reservas pendientes pueden ser confirmadas");
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return reservaMapper.toDto(reservaRepo.save(reserva));
    }

    @Override
    public List<ReservaResponseDto> obtenerReservasPorUsuario(Long userId) {
        return reservaRepo.findByUsuarioId(userId)
                .stream().map(reservaMapper::toDto).toList();
    }

    @Override
    public List<ReservaResponseDto> obtenerTodasLasReservas() {
        return reservaRepo.findAllConServicios()
                .stream()
                .map(reservaMapper::toDto)
                .toList();
    }


    private double obtenerPrecioServicio(ReservaServicioDto dto, Servicio servicio) {
        return (dto.getValorServicio() != null && dto.getValorServicio() > 0)
                ? dto.getValorServicio()
                : Optional.ofNullable(servicio.getValor()).orElse(0.0);
    }

    @Scheduled(fixedRate =  30 * 60 * 1000)
    @Transactional
    public void actualizarReservasFinalizadas() {
        int finalizada = reservaRepo.marcarReservasFinalizadas(
                EstadoReserva.FINALIZADA,
                LocalDate.now(),
                LocalTime.now(),
                List.of(EstadoReserva.PENDIENTE, EstadoReserva.CONFIRMADA)
        );
        log.info("Se finalizo {} reservas vencidas", finalizada);

    }

    @Transactional
    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void cancelarReservasPendientesExpiradas() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(15);
        int actualizadas = reservaRepo.cancelarReservasPendientesExpiradas(limite, EstadoReserva.CANCELADA, EstadoReserva.PENDIENTE);
        log.info("Reservas pendientes canceladas automáticamente: " + actualizadas);
    }
}
