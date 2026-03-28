package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Reserva;
import com.svalero.eventia.exception.ErrorResponse;
import com.svalero.eventia.exception.ReservaNotFoundException;
import com.svalero.eventia.exception.UsuarioNotFoundException;
import com.svalero.eventia.service.ReservaService;
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
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.findAll();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<Reserva> getReserva(@PathVariable long id) throws ReservaNotFoundException {
        Reserva reserva = reservaService.findById(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping("/reservas")
    public ResponseEntity<Reserva> addReserva(@Valid  @RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.add(reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable long id) throws ReservaNotFoundException {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<Reserva> modifyReserva(@PathVariable long id, @Valid @RequestBody Reserva reserva) throws ReservaNotFoundException {
        Reserva reservaModificada = reservaService.modify(id, reserva);
        return ResponseEntity.ok(reservaModificada);
    }


    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ReservaNotFoundException rnfe) {
        return new ResponseEntity<>(ErrorResponse.notFound(rnfe.getMessage()), HttpStatus.NOT_FOUND);
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