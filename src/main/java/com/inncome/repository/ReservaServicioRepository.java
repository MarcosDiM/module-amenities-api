package com.inncome.repository;

import com.inncome.model.ReservaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaServicioRepository extends JpaRepository<ReservaServicio, Long> {
}
