package com.inncome.repository;

import com.inncome.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    List<Imagen> findByAmenitieId(Long amenitieId);
}

