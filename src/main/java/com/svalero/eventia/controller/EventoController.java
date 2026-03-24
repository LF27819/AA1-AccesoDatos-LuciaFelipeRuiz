package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Evento;
import com.svalero.eventia.exception.EventoNotFoundException;
import com.svalero.eventia.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventos")
    public List<Evento> getAllEventos() {
        return eventoService.findAll();
    }

    @GetMapping("/eventos/{id}")
    public Evento getEvento(@PathVariable Long id) throws EventoNotFoundException {
        return eventoService.findById(id);
    }

    @PostMapping("/eventos")
    public Evento addEvento(@RequestBody Evento evento) {
        return eventoService.add(evento);
    }

    @DeleteMapping("/eventos/{id}")
    public void deleteEvento(@PathVariable Long id) throws EventoNotFoundException {
        eventoService.delete(id);
    }

    @PutMapping("/eventos/{id}")
    public Evento modifyEvento(@PathVariable Long id, @RequestBody Evento evento) throws EventoNotFoundException {
        return eventoService.modify(id, evento);
    }
}