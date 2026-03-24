package com.svalero.eventia.repository;

import com.svalero.eventia.domain.Recinto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecintoRepository extends CrudRepository<Recinto, Long> {

    List<Recinto> findAll();
}