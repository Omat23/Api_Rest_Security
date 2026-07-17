package com.example.api_rest_security.exceptions.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component //Marcamos la clase como un componente gestionado por Spring
/*
    La interfaz AuthenticationEntryPoint es la encargada de capturar las excepciones de tipo 401 UNAUTHORIZED.
    En este caso, cuando el usuario intenta acceder a un recurso del sistema el cual esta protegido y requiere de
    una autenticacion previa.
 */
public class CustomAuthenticationEntryPoint extends AuthenticationException implements AuthenticationEntryPoint {

    //Heredamos el constructor de la clase padre AuthenticationEntryPoint
    public CustomAuthenticationEntryPoint(HandlerExceptionResolver handlerExceptionResolver) {
        super(handlerExceptionResolver);
    }

    //Sobreescribimos el metodo commence() de la interfaz AuthenticacionEntryPoint
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        // En lugar de escribir el JSON aquí, delegamos la excepción al GlobalExceptionHandler
        super.handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
