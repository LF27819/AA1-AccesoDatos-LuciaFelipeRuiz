package com.svalero.eventia.controller;

import com.svalero.eventia.domain.Artista;
import com.svalero.eventia.exception.ArtistaNotFoundException;
import com.svalero.eventia.exception.ErrorResponse;
import com.svalero.eventia.service.ArtistaService;
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
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/artistas")
    public ResponseEntity<List<Artista>> getAllArtistas(
            @RequestParam(required = false) String nombreArtistico,
            @RequestParam(required = false) String generoMusical,
            @RequestParam(required = false) Boolean activo) {

        List<Artista> artistas = artistaService.findAll(nombreArtistico, generoMusical,activo);
        return ResponseEntity.ok(artistas);
    }

    @GetMapping("/artistas/{id}")
    public ResponseEntity<Artista> getArtista(@PathVariable long id) throws ArtistaNotFoundException {
        Artista artista = artistaService.findById(id);
        return ResponseEntity.ok(artista);
    }

    @PostMapping("/artistas")
    public ResponseEntity<Artista> addArtista(@Valid  @RequestBody Artista artista) {
        Artista nuevoArtista = artistaService.add(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
    }

    @DeleteMapping("/artistas/{id}")
    public ResponseEntity<Void> deleteArtista(@PathVariable long id) throws ArtistaNotFoundException {
        artistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/artistas/{id}")
    public ResponseEntity<Artista> modifyArtista(@PathVariable long id, @Valid @RequestBody Artista artista) throws ArtistaNotFoundException {
        Artista artistaModificado = artistaService.modify(id, artista);
        return ResponseEntity.ok(artistaModificado);
    }

    @PatchMapping("/artistas/{id}")
    public ResponseEntity<Artista> patchArtista(@PathVariable long id,
                                                @RequestBody Map<String, Object> updates) throws ArtistaNotFoundException {
        Artista artistaActualizado = artistaService.patch(id, updates);
        return ResponseEntity.ok(artistaActualizado);
    }



    @ExceptionHandler(ArtistaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ArtistaNotFoundException anfe) {
        return new ResponseEntity<>(ErrorResponse.notFound(anfe.getMessage()), HttpStatus.NOT_FOUND);
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