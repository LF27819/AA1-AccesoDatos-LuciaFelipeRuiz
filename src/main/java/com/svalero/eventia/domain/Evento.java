package com.svalero.eventia.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @NotNull
    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;
    @NotNull
    @Column(name = "hora_evento")
    private LocalTime horaEvento;
    @PositiveOrZero
    @Column(name = "precio_entrada")
    private float precioEntrada;
    @Positive
    @Column(name = "aforo_maximo")
    private int aforoMaximo;
    @PositiveOrZero
    @Column(name = "entradas_disponibles")
    private int entradasDisponibles;
    @Column
    private boolean cancelado;
    @Column
    private boolean presencial;
    @Column
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @ManyToOne
    @JoinColumn(name = "recinto_id")
    private Recinto recinto;
}