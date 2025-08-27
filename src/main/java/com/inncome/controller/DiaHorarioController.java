package com.inncome.controller;

import com.inncome.dto.DiaHorario.DiaHorarioRequestDto;
import com.inncome.dto.DiaHorario.DiaHorarioResponseDto;
import com.inncome.model.Amenitie;
import com.inncome.model.DiaHorario;
import com.inncome.service.AmenitieService;
import com.inncome.service.DiaHorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/diaHorario")
//@PreAuthorize("hasAuthority('ADMIN')")
public class DiaHorarioController {

    private final DiaHorarioService diaHorarioService;
    private final AmenitieService amenitieService;

    @GetMapping("/lista")
    public ResponseEntity<List<DiaHorarioResponseDto>> listar() {
        return ResponseEntity.ok(diaHorarioService.listar());
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<DiaHorarioResponseDto> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody DiaHorarioRequestDto dto) {
        return ResponseEntity.ok(diaHorarioService.actualizar(id, dto));
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        diaHorarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{amenitieId}")
    ResponseEntity<DiaHorario> agregarDiaHorario(@PathVariable Long amenitieId, @RequestBody DiaHorarioRequestDto diaHorarioDto) {
        Amenitie amenitie = amenitieService.findAmenitieById(amenitieId);
        DiaHorario diaHorario = diaHorarioService.agregarDiaHorario(amenitie, diaHorarioDto);
        return ResponseEntity.ok(diaHorario);
    }

    @PatchMapping("/{amenitieId}/{diaHorarioId}/eliminar")
    ResponseEntity<DiaHorario> eliminarDiaHorario(@PathVariable Long amenitieId, @PathVariable Long diaHorarioId) {
        Amenitie amenitie = amenitieService.findAmenitieById(amenitieId);
        diaHorarioService.eliminarDiaHorario(amenitie, diaHorarioId);
        return ResponseEntity.ok().build();
    }
}