package com.example.api_rest_security.controller;

import com.example.api_rest_security.dtos.request.UserLoginRequestDto;
import com.example.api_rest_security.dtos.request.UserRegisterRequestDto;
import com.example.api_rest_security.dtos.response.UserResponseDto;
import com.example.api_rest_security.jwt.JwtService;
import com.example.api_rest_security.service.AuthenticationService;
import com.example.api_rest_security.service.CustomUserDetailsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    //Solicitamos un authenticationManager para poder autenticar las peticiones HTTP que lleguen al controlador de autenticacion
    private final AuthenticationManager authenticationManager;

    //Inyectamos
    private final CustomUserDetailsService customUserDetailsService;

    private final JwtService jwtService;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody //Solicitamos un objeto de tipo userLoginRequestDto que se debera enviar en formato JSON
            UserLoginRequestDto userLoginRequestDto){

        /*
         * Mandamos a llamar a nuestro authenticationManager para autenticar las credenciales enviadas
         * por parte del cliente mediante su metodo 'authenticate() donde instanciaremos un objeto de tipo
         * UsernamePasswordAuthenticationToken el cual envolvera los datos enviados del cliente y se los trasnfiere
         * al proveedor de autenticacion del AuthenticationManager. Si la autenticacion es correcta se devuelve un objeto
         * de tipo Authenticate con el usuario ya autenticado.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.getEmail(),
                        userLoginRequestDto.getPassword()));

        //Traemos el usuario autenticado de la base de datos
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginRequestDto.getEmail());

        //Generamos el token con base al usuario autenticado
        String token = jwtService.generateToken(userDetails);

        //Regresamos el token junto con una respuesta 200 OK
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
