package com.inncome.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.Where;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "amenities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Amenitie extends Base {

    @Column(name = "establecimiento_id", nullable = false)
    private Long establecimientoId;

    @Column(name = "nombre_amenitie", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = true)
    private Double precio;

    @Column(name = "capacidad_maxima", nullable = true)
    private Integer capacidadMaxima;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @Column(name ="mensaje", nullable = false)
    private String mensaje;

    @Column(name = "url_reglamento", nullable = false)
    private String urlReglamento;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "amenities_servicios",
            joinColumns = @JoinColumn(name = "amenitie_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Servicio> servicios = new HashSet<>();

    @Where(clause = "activo = true")
    @OneToMany(mappedBy = "amenitie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<DiaHorario> diasHorarios = new HashSet<>();

    @OneToMany(mappedBy = "amenitie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Imagen> imagenes = new HashSet<>();

    @Column(name = "duracion_reserva", nullable = false)
    private Integer duracionReserva;
}
