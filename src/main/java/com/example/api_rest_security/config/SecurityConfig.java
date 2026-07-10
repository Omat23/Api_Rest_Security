package com.example.api_rest_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Marcamos la clase como una clase de configuracion y gestor de beans
public class SecurityConfig {

    @Bean // Marcamos el metodo como un bean que sera gestionado por Spring
    // Creamos el objeto Encoder para encriptar passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
