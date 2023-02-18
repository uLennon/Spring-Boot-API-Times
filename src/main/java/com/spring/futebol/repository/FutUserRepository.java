package com.spring.futebol.repository;

import com.spring.futebol.domain.FutUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutUserRepository extends JpaRepository<FutUser, Long> {
    FutUser findByUsername(String username);
}
