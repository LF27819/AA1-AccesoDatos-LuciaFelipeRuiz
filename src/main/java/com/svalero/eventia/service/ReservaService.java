package com.svalero.eventia.service;

import com.svalero.eventia.domain.Reserva;
import com.svalero.eventia.exception.ReservaNotFoundException;
import com.svalero.eventia.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Reserva findById(Long id) throws ReservaNotFoundException {
        return reservaRepository.findById(id)
                .orElseThrow(ReservaNotFoundException::new);
    }

    public Reserva add(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void delete(Long id) throws ReservaNotFoundException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(ReservaNotFoundException::new);

        reservaRepository.delete(reserva);
    }

    public Reserva modify(Long id, Reserva nuevaReserva) throws ReservaNotFoundException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(ReservaNotFoundException::new);

        reserva.setFechaReserva(nuevaReserva.getFechaReserva());
        reserva.setCantidadEntradas(nuevaReserva.getCantidadEntradas());
        reserva.setPrecioTotal(nuevaReserva.getPrecioTotal());
        reserva.setMetodoPago(nuevaReserva.getMetodoPago());
        reserva.setCodigoReserva(nuevaReserva.getCodigoReserva());
        reserva.setConfirmada(nuevaReserva.isConfirmada());
        reserva.setUsuario(nuevaReserva.getUsuario());
        reserva.setEvento(nuevaReserva.getEvento());

        return reservaRepository.save(reserva);
    }
}