package com.svalero.eventia.service;

import com.svalero.eventia.domain.Artista;
import com.svalero.eventia.exception.ArtistaNotFoundException;
import com.svalero.eventia.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> findAll() {
        return artistaRepository.findAll();
    }

    public Artista findById(Long id) throws ArtistaNotFoundException {
        return artistaRepository.findById(id)
                .orElseThrow(ArtistaNotFoundException::new);
    }

    public Artista add(Artista artista) {
        return artistaRepository.save(artista);
    }

    public void delete(Long id) throws ArtistaNotFoundException {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(ArtistaNotFoundException::new);

        artistaRepository.delete(artista);
    }

    public Artista modify(Long id, Artista nuevoArtista) throws ArtistaNotFoundException {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(ArtistaNotFoundException::new);

        artista.setNombreArtistico(nuevoArtista.getNombreArtistico());
        artista.setNombreReal(nuevoArtista.getNombreReal());
        artista.setGeneroMusical(nuevoArtista.getGeneroMusical());
        artista.setFechaNacimiento(nuevoArtista.getFechaNacimiento());
        artista.setActivo(nuevoArtista.isActivo());
        artista.setCache(nuevoArtista.getCache());
        artista.setEventosRealizados(nuevoArtista.getEventosRealizados());

        return artistaRepository.save(artista);
    }
}