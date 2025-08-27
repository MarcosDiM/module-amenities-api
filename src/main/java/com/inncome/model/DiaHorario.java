    package com.inncome.model;

    import com.inncome.model.enums.DiaSemana;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalTime;

    @EqualsAndHashCode(callSuper = true)
    @Entity
    @Table(name = "dia_horario")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class DiaHorario extends Base {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenitie_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Amenitie amenitie;

}

