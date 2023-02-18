package com.spring.futebol.service;

import com.spring.futebol.repository.FutUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class FutUserDetailsService implements UserDetailsService {
    @Autowired
    private FutUserRepository futUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(futUserRepository.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("Usuario nao encontrado!"));
    }

}
