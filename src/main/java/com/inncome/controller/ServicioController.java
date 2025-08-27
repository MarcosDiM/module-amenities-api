package com.inncome.controller;

import com.inncome.dto.ServicioDto;
import com.inncome.model.Servicio;
import com.inncome.service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/servicios")
@RestController
@RequiredArgsConstructor
public class ServicioController {
    private final ServicioService servicioService;

    @GetMapping
    ResponseEntity<List<Servicio>> obtenerServicios() {
        List<Servicio> servicios = servicioService.getAllServicios();
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/establecimiento/{usuarioCreadorId}")
    ResponseEntity<List<Servicio>> obtenerServiciosPorEstablecimiento(@PathVariable Long usuarioCreadorId) {
        List<Servicio> servicios = servicioService.getServiciosByeEstablecimiento(usuarioCreadorId);
        return ResponseEntity.ok(servicios);
    }

    @PostMapping
    ResponseEntity<Servicio> crearServicio(@Valid @RequestBody ServicioDto servicioNuevo){
        Servicio servicioCreado = servicioService.createServicio(servicioNuevo);
        return ResponseEntity.ok(servicioCreado);
    }

    @PutMapping("/{servicioId}")
    ResponseEntity<Servicio> editarServicio(@PathVariable Long servicioId,@Valid @RequestBody ServicioDto servicioDto) {
        Servicio servicioEditado = servicioService.editServicio(servicioId, servicioDto);
        return ResponseEntity.ok(servicioEditado);
    }

    @PutMapping("/{servicioId}/desactivar")
    ResponseEntity<Void> desactivarServicio(@PathVariable Long servicioId) {
        servicioService.deleteServicio(servicioId);
        return ResponseEntity.ok(null);
    }
}