package com.inncome.repository;

import com.inncome.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findAllByActivoTrue();
    List<Servicio> findByUsuarioCreadorIdAndActivoTrue(Long establecimientoId);

}
