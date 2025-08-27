package com.inncome.controller;

import com.inncome.dto.Amenitie.AmenitieRequestDto;
import com.inncome.dto.Amenitie.AmenitieDto;
import com.inncome.model.Amenitie;
import com.inncome.service.AmenitieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/amenities")
@RestController
@RequiredArgsConstructor
public class AmentieController {

    private final AmenitieService amenitieService;

    @GetMapping("/{amenitieId}")
    ResponseEntity<Amenitie> getAmenitieById(@PathVariable Long amenitieId) {
        Amenitie amenitie = amenitieService.findAmenitieById(amenitieId);
        return ResponseEntity.ok(amenitie);
    }

    @GetMapping("/establecimiento/{establecimientoId}")
    ResponseEntity<List<Amenitie>> getAmenititiesByEstablecimiento(@PathVariable Long establecimientoId) {
        List<Amenitie> amenities = amenitieService.getAmenitiesByEstablecimiento(establecimientoId);
        return ResponseEntity.ok(amenities);
    }

    @PostMapping
    ResponseEntity<Amenitie> createAmenitie(@RequestBody AmenitieRequestDto amenitie) throws ChangeSetPersister.NotFoundException {
        Amenitie createdAmenitie = amenitieService.createAmenitie(amenitie);
        return ResponseEntity.ok(createdAmenitie);
    }

    @PutMapping("/editar")
    ResponseEntity<Amenitie> updateAmenitie( @RequestBody AmenitieDto amenitieDto) {
        Amenitie updatedAmenitie = amenitieService.editAmenitie(amenitieDto);
        return ResponseEntity.ok(updatedAmenitie);
    }

    @PatchMapping("/{amenitieId}/cambiar-estado")
    ResponseEntity<Void> cambiarEstadoAmenitie(@RequestBody Boolean estado, @PathVariable Long amenitieId) {
        amenitieService.changeStatusAmenitie(amenitieId, estado);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{amenitieId}/servicios/{servicioId}")
    ResponseEntity<Amenitie> agregarServicioAmenitie(@PathVariable Long amenitieId, @PathVariable Long servicioId) {
        Amenitie updatedAmenitie = amenitieService.agregarServicioAAmenitie(amenitieId, servicioId);
        return ResponseEntity.ok(updatedAmenitie);
    }

    @PutMapping("/{amenitieId}/servicios/{servicioId}/eliminar")
    ResponseEntity<Amenitie> eliminarServicioAmenitie(@PathVariable Long amenitieId, @PathVariable Long servicioId) {
        amenitieService.quitarServicioDeAmenitie(amenitieId, servicioId);
        return ResponseEntity.ok().build();
    }
}
