package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Recinto;
import com.svalero.eventia.exception.ErrorResponse;
import com.svalero.eventia.exception.RecintoNotFoundException;
import com.svalero.eventia.service.RecintoService;
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
public class RecintoController {

    @Autowired
    private RecintoService recintoService;

    @GetMapping("/recintos")
    public ResponseEntity<List<Recinto>> getAllRecintos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) Boolean cubierto) {

        List<Recinto> recintos = recintoService.findAll(nombre, ciudad, cubierto);
        return ResponseEntity.ok(recintos);
    }

    @GetMapping("/recintos/{id}")
    public ResponseEntity<Recinto> getRecinto(@PathVariable long id) throws RecintoNotFoundException {
        Recinto recinto = recintoService.findById(id);
        return ResponseEntity.ok(recinto);
    }

    @PostMapping("/recintos")
    public ResponseEntity<Recinto> addRecinto(@Valid  @RequestBody Recinto recinto) {
        Recinto nuevoRecinto = recintoService.add(recinto);
        return new ResponseEntity<>(nuevoRecinto, HttpStatus.CREATED);
    }

    @DeleteMapping("/recintos/{id}")
    public ResponseEntity<Void> deleteRecinto(@PathVariable long id) throws RecintoNotFoundException {
        recintoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/recintos/{id}")
    public ResponseEntity<Recinto> modifyRecinto(@PathVariable long id,@Valid @RequestBody Recinto recinto) throws RecintoNotFoundException {
        Recinto recintoModificado = recintoService.modify(id, recinto);
        return ResponseEntity.ok(recintoModificado);
    }

    @PatchMapping("/recintos/{id}")
    public ResponseEntity<Recinto> patchRecinto(@PathVariable long id,
                                                @RequestBody Map<String, Object> updates) throws RecintoNotFoundException {
        Recinto recintoActualizado = recintoService.patch(id, updates);
        return ResponseEntity.ok(recintoActualizado);
    }



    @ExceptionHandler(RecintoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(RecintoNotFoundException rnfe) {
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