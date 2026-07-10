package com.example.api_rest_security.controller;

import com.example.api_rest_security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Indicamos que la clase sera un controlador de una aplicacion web, y que los metodos de la clase seran manejadores de peticiones HTTP
@RequestMapping("/api/auth") //Indicamos la ruta base para todas las peticiones que se realicen a este controlador. En este caso /api/auth
@AllArgsConstructor //Indicamos la generacion automatica de un constructor con todos los atributos de la clase
public class AuthController {

    //Solicitamos un authenticationService para poder utilizar la logica de autenticacion de sus metodos
    private final AuthenticationService authenticationService;

}
