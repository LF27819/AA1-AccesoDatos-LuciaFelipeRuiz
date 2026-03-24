package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Reserva;
import com.svalero.eventia.exception.ReservaNotFoundException;
import com.svalero.eventia.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/reservas")
    public List<Reserva> getAllReservas() {
        return reservaService.findAll();
    }

    @GetMapping("/reservas/{id}")
    public Reserva getReserva(@PathVariable Long id) throws ReservaNotFoundException {
        return reservaService.findById(id);
    }

    @PostMapping("/reservas")
    public Reserva addReserva(@RequestBody Reserva reserva) {
        return reservaService.add(reserva);
    }

    @DeleteMapping("/reservas/{id}")
    public void deleteReserva(@PathVariable Long id) throws ReservaNotFoundException {
        reservaService.delete(id);
    }

    @PutMapping("/reservas/{id}")
    public Reserva modifyReserva(@PathVariable Long id, @RequestBody Reserva reserva) throws ReservaNotFoundException {
        return reservaService.modify(id, reserva);
    }
}