package com.svalero.eventia.service;

import com.svalero.eventia.domain.Recinto;
import com.svalero.eventia.exception.RecintoNotFoundException;
import com.svalero.eventia.repository.RecintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecintoService {

    @Autowired
    private RecintoRepository recintoRepository;

    public List<Recinto> findAll() {
        return recintoRepository.findAll();
    }

    public Recinto findById(Long id) throws RecintoNotFoundException {
        return recintoRepository.findById(id)
                .orElseThrow(RecintoNotFoundException::new);
    }

    public Recinto add(Recinto recinto) {
        return recintoRepository.save(recinto);
    }

    public void delete(Long id) throws RecintoNotFoundException {
        Recinto recinto = recintoRepository.findById(id)
                .orElseThrow(RecintoNotFoundException::new);

        recintoRepository.delete(recinto);
    }

    public Recinto modify(Long id, Recinto nuevoRecinto) throws RecintoNotFoundException {
        Recinto recinto = recintoRepository.findById(id)
                .orElseThrow(RecintoNotFoundException::new);

        recinto.setNombre(nuevoRecinto.getNombre());
        recinto.setDireccion(nuevoRecinto.getDireccion());
        recinto.setCiudad(nuevoRecinto.getCiudad());
        recinto.setCapacidad(nuevoRecinto.getCapacidad());
        recinto.setCubierto(nuevoRecinto.isCubierto());
        recinto.setPrecioAlquiler(nuevoRecinto.getPrecioAlquiler());
        recinto.setEventosCelebrados(nuevoRecinto.getEventosCelebrados());
        recinto.setFechaInauguracion(nuevoRecinto.getFechaInauguracion());

        return recintoRepository.save(recinto);
    }

    public List<Recinto> findAll(String nombre, String ciudad, Boolean cubierto) {
        return recintoRepository.findByFilters(nombre, ciudad, cubierto);
    }
}