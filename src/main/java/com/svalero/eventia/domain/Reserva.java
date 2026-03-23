package com.svalero.eventia.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;
    @Min(value = 0 )
    @Column(name = "cantidad_entradas")
    private int cantidadEntradas;
    @NotNull
    @Min(value = 0 )
    @Column(name = "precio_total")
    private float precioTotal;
    @Column(name = "metodo_pago")
    private String metodoPago;
    @NotNull
    @Column(name= "codigo_reserva", unique = true)
    private String codigoReserva;
    @Column
    private boolean confirmada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}