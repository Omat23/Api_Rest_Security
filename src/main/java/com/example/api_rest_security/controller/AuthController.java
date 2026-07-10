package com.example.api_rest_security.controller;

import com.example.api_rest_security.dtos.request.UserRegisterRequestDto;
import com.example.api_rest_security.dtos.response.UserResponseDto;
import com.example.api_rest_security.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indicamos que la clase sera un controlador de una aplicacion web, y que los
                // metodos de la clase seran manejadores de peticiones HTTP
@RequestMapping("/api/auth") // Indicamos la ruta base para todas las peticiones que se realicen a este
                             // controlador. En este caso /api/auth
@AllArgsConstructor // Indicamos la generacion automatica de un constructor con todos los atributos
                    // de la clase
public class AuthController {

    // Solicitamos un authenticationService para poder utilizar la logica de
    // autenticacion de sus metodos
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid // Indicamos que vamos a capturar las excepciones de validacion de campos en
                   // caso de que se produzcan
            @RequestBody // Indicamos que requerimos de un objeto completo de este tipo
            UserRegisterRequestDto userRegisterRequestDto) {

        // Solicitamos el servicio de autenticacion para que nos devuelva un usuario
        // creado
        UserResponseDto userResponseDto = authenticationService.registerUser(userRegisterRequestDto);

        // Retornamos el usuario creado y un codigo de estado HTTP 201
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

}
