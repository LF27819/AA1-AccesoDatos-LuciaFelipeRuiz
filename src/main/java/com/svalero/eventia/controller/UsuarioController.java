package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Usuario;
import com.svalero.eventia.exception.ErrorResponse;
import com.svalero.eventia.exception.UsuarioNotFoundException;
import com.svalero.eventia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String rol) {

        List<Usuario> usuarios = usuarioService.findAll(nombre, email, rol);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable long id) throws UsuarioNotFoundException {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> addUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.add(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable long id) throws UsuarioNotFoundException {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> modifyUsuario(@PathVariable long id, @Valid @RequestBody Usuario usuario) throws UsuarioNotFoundException {
        Usuario usuarioModificado = usuarioService.modify(id, usuario);
        return ResponseEntity.ok(usuarioModificado);
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> patchUsuario(@PathVariable long id,
                                                @RequestBody Map<String, Object> updates) throws UsuarioNotFoundException {
        Usuario usuarioActualizado = usuarioService.patch(id, updates);
        return ResponseEntity.ok(usuarioActualizado);
    }



    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsuarioNotFoundException unfe) {
        return new ResponseEntity<>(ErrorResponse.notFound(unfe.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorResponse errorResponse = ErrorResponse.validationError(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                ErrorResponse.generalError(500, "internal-server-error", "Error interno del servidor"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
