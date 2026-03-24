package com.svalero.eventia.repository;

import com.svalero.eventia.domain.Artista;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistaRepository extends CrudRepository<Artista, Long> {

    List<Artista> findAll();
}