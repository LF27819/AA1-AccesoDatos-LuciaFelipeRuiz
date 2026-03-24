package com.svalero.eventia.repository;

import com.svalero.eventia.domain.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends CrudRepository<Evento, Long> {

    List<Evento> findAll();
}