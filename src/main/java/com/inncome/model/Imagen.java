package com.inncome.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "imagen")
@Getter
@Setter
public class Imagen extends Base{

    @Column(nullable = false)
    private String url;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "amenitie_id")
    private Amenitie amenitie;
}

