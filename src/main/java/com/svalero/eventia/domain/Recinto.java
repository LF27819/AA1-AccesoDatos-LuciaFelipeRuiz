package com.svalero.eventia.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recintos")
public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column
    private String nombre;
    @NotNull
    @Column
    private String direccion;
    @NotNull
    @Column
    private String ciudad;
    @Min(value = 0 )
    @Column
    private int capacidad;
    @Column
    private boolean cubierto;
    @Min(value = 0 )
    @Column(name = "precio_alquiler")
    private float precioAlquiler;
    @Min(value = 0 )
    @Column(name = "eventos_celebrados")
    private int eventosCelebrados;
    @Column(name = "fecha_inauguracion")
    private LocalDate fechaInauguracion;
}