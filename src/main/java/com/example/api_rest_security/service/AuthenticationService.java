package com.example.api_rest_security.service;

import com.example.api_rest_security.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service //Marcamos esta clase como un servicio que podra gestionar Spring
@AllArgsConstructor //Indicamos la generacion automatica de un constructor con todos los atributos de la clase
public class AuthenticationService {

    //Atributo requerido para guardar los usuarios en la base de datos
    private final UserJpaRepository userJpaRepository;

}
