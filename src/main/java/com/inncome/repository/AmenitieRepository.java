package com.inncome.repository;

import com.inncome.model.Amenitie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AmenitieRepository extends JpaRepository<Amenitie, Long> {
    List<Amenitie> findByEstablecimientoIdAndActivo(Long establecimientoId, Boolean active);
}
