package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Artista;
import com.svalero.eventia.exception.ArtistaNotFoundException;
import com.svalero.eventia.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/artistas")
    public List<Artista> getAllArtistas() {
        return artistaService.findAll();
    }

    @GetMapping("/artistas/{id}")
    public Artista getArtista(@PathVariable Long id) throws ArtistaNotFoundException {
        return artistaService.findById(id);
    }

    @PostMapping("/artistas")
    public Artista addArtista(@RequestBody Artista artista) {
        return artistaService.add(artista);
    }

    @DeleteMapping("/artistas/{id}")
    public void deleteArtista(@PathVariable Long id) throws ArtistaNotFoundException {
        artistaService.delete(id);
    }

    @PutMapping("/artistas/{id}")
    public Artista modifyArtista(@PathVariable Long id, @RequestBody Artista artista) throws ArtistaNotFoundException {
        return artistaService.modify(id, artista);
    }
}