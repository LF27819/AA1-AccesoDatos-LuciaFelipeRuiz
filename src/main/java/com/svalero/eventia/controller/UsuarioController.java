package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Usuario;
import com.svalero.eventia.exception.UsuarioNotFoundException;
import com.svalero.eventia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario getUsuario(@PathVariable Long id) throws UsuarioNotFoundException {
        return usuarioService.findById(id);
    }

    @PostMapping("/usuarios")
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        return usuarioService.add(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuario(@PathVariable Long id) throws UsuarioNotFoundException {
        usuarioService.delete(id);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario modifyUsuario(@PathVariable Long id, @RequestBody Usuario usuario) throws UsuarioNotFoundException {
        return usuarioService.modify(id, usuario);
    }
}