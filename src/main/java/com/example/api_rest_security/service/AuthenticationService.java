package com.example.api_rest_security.service;

import com.example.api_rest_security.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserJpaRepository userJpaRepository;

}
