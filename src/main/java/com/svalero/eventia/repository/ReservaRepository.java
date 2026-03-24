package com.svalero.eventia.repository;

import com.svalero.eventia.domain.Reserva;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {

    List<Reserva> findAll();
}
