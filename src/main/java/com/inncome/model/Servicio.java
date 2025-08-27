package com.inncome.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Servicio extends Base {

    @Column(name = "nombre_servicio", nullable = false, length = 50)
    private String nombreServicio;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "obligatoriedad", nullable = false)
    private Boolean  obligatoriedad;

    @Column(name = "valor", nullable = true)
    private Double valor;

    @Column(name = "usuario_creador_id", nullable = false)
    private Long usuarioCreadorId;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<ReservaServicio> reservaServicios = new ArrayList<>();

}
