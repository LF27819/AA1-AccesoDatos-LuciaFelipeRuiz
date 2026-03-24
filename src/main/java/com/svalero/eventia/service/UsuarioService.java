package com.svalero.eventia.service;

import com.svalero.eventia.domain.Usuario;
import com.svalero.eventia.exception.UsuarioNotFoundException;
import com.svalero.eventia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) throws UsuarioNotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario add(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) throws UsuarioNotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);

        usuarioRepository.delete(usuario);
    }

    public Usuario modify(Long id, Usuario nuevoUsuario) throws UsuarioNotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);

        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setApellidos(nuevoUsuario.getApellidos());
        usuario.setEmail(nuevoUsuario.getEmail());
        usuario.setPassword(nuevoUsuario.getPassword());
        usuario.setTelefono(nuevoUsuario.getTelefono());
        usuario.setActivo(nuevoUsuario.isActivo());
        usuario.setFechaNacimiento(nuevoUsuario.getFechaNacimiento());
        usuario.setEventosAsistidos(nuevoUsuario.getEventosAsistidos());
        usuario.setRol(nuevoUsuario.getRol());
        usuario.setSaldoCuenta(nuevoUsuario.getSaldoCuenta());

        return usuarioRepository.save(usuario);
    }
}