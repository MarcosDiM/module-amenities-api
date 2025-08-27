package com.inncome.controller;

import com.inncome.dto.Reserva.ReservaRequestDto;
import com.inncome.dto.Reserva.ReservaResponseDto;
import com.inncome.model.enums.EstadoReserva;
import com.inncome.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<ReservaResponseDto> crearReserva(
            @RequestBody ReservaRequestDto reservaRequestDto,
            @RequestHeader("X-User-Id") Long usuarioId
    ) {
        ReservaResponseDto responseDto = reservaService.crearReserva(reservaRequestDto, usuarioId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDto> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponseDto> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.confirmarReserva(id));
    }

    @GetMapping("/estados")
    public List<String> getEstadosReserva() {
        return Arrays.stream(EstadoReserva.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservaResponseDto>> listarPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorUsuario(userId));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> listarTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodasLasReservas());
    }
}

