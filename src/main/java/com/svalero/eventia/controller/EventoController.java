package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Evento;
import com.svalero.eventia.exception.ErrorResponse;
import com.svalero.eventia.exception.EventoNotFoundException;
import com.svalero.eventia.exception.RecintoNotFoundException;
import com.svalero.eventia.service.EventoService;
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
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> getAllEventos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean cancelado) {

        List<Evento> eventos = eventoService.findAll(nombre, categoria, cancelado);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/eventos/{id}")
    public ResponseEntity<Evento> getEvento(@PathVariable long id) throws EventoNotFoundException {
        Evento evento = eventoService.findById(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping("/eventos")
    public ResponseEntity<Evento> addEvento(@Valid @RequestBody Evento evento) {
        Evento nuevoEvento = eventoService.add(evento);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable long id) throws EventoNotFoundException {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/eventos/{id}")
    public ResponseEntity<Evento> modifyEvento(@PathVariable long id, @Valid @RequestBody Evento evento) throws EventoNotFoundException {
        Evento eventoModificado = eventoService.modify(id, evento);
        return ResponseEntity.ok(eventoModificado);
    }

    @PatchMapping("/eventos/{id}")
    public ResponseEntity<Evento> patchEvento(@PathVariable long id,
                                              @RequestBody Map<String, Object> updates) throws EventoNotFoundException {
        Evento eventoActualizado = eventoService.patch(id, updates);
        return ResponseEntity.ok(eventoActualizado);
    }



    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EventoNotFoundException enfe) {
        return new ResponseEntity<>(ErrorResponse.notFound(enfe.getMessage()), HttpStatus.NOT_FOUND);
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