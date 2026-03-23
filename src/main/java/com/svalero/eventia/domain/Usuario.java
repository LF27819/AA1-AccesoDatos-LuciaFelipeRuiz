package com.svalero.eventia.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column
    private String nombre;
    @NotNull
    @Column
    private String apellidos;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @Column
    @Size(min = 6, max = 20)
    private String password;
    @Column
    private String telefono;
    @Column
    private boolean activo;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "eventos_asistidos")
    private int eventosAsistidos;
    @Column
    private String rol;
    @Min(value = 0 )
    @Column(name = "saldo_cuenta")
    private float saldoCuenta;
}