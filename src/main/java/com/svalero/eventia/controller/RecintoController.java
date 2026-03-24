package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Recinto;
import com.svalero.eventia.exception.RecintoNotFoundException;
import com.svalero.eventia.service.RecintoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecintoController {

    @Autowired
    private RecintoService recintoService;

    @GetMapping("/recintos")
    public List<Recinto> getAllRecintos() {
        return recintoService.findAll();
    }

    @GetMapping("/recintos/{id}")
    public Recinto getRecinto(@PathVariable Long id) throws RecintoNotFoundException {
        return recintoService.findById(id);
    }

    @PostMapping("/recintos")
    public Recinto addRecinto(@RequestBody Recinto recinto) {
        return recintoService.add(recinto);
    }

    @DeleteMapping("/recintos/{id}")
    public void deleteRecinto(@PathVariable Long id) throws RecintoNotFoundException {
        recintoService.delete(id);
    }

    @PutMapping("/recintos/{id}")
    public Recinto modifyRecinto(@PathVariable Long id, @RequestBody Recinto recinto) throws RecintoNotFoundException {
        return recintoService.modify(id, recinto);
    }
}