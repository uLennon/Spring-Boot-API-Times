package com.spring.futebol.repository;

import com.spring.futebol.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time,Long> {

    List<Time> findByName(String nome);


}
