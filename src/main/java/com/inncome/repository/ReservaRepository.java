package com.inncome.repository;

import com.inncome.model.Reserva;

import com.inncome.model.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
        select case when count(r) > 0 then true else false end
        from Reserva r
        where r.amenitie.id = :amenitieId
          and r.fecha = :fecha
          and r.estado != :estadoExcluido
          and (
                (r.horaInicio < :horaFin and r.horaFin > :horaInicio)
              )
    """)
    boolean existsConflictingReservation(
            @Param("amenitieId") Long amenitieId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("estadoExcluido") EstadoReserva estadoExcluido
    );

    @Modifying
    @Transactional
    @Query("""
    update Reserva r set r.estado = :estadoFinalizado
    where (r.fecha < :hoy or (r.fecha = :hoy and r.horaFin <= :ahora))
      and r.estado in :estados
    """)
    int marcarReservasFinalizadas(
            @Param("estadoFinalizado") EstadoReserva estadoFinalizado,
            @Param("hoy") LocalDate hoy,
            @Param("ahora") LocalTime ahora,
            @Param("estados") List<EstadoReserva> estados);

    @Modifying // Indica que esta query modifica datos
    @Query("""
        UPDATE Reserva r
        SET r.estado = :estadoCancelado
        WHERE r.estado = :estadoPendiente
          AND r.fechaCreacion < :limite
    """)
    int cancelarReservasPendientesExpiradas(
            @Param("limite") LocalDateTime limite,
            @Param("estadoCancelado") EstadoReserva estadoCancelado,
            @Param("estadoPendiente") EstadoReserva estadoPendiente
    );

    @Query("SELECT r FROM Reserva r LEFT JOIN FETCH r.reservaServicios")
    List<Reserva> findAllConServicios();

    List<Reserva> findByUsuarioId(Long usuarioId);
}



