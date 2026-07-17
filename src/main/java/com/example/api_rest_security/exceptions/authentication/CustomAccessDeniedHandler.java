package com.example.api_rest_security.exceptions.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component //Marcamos la clase como un componente que puede gestionar Spring
/*
    AccessDeniedHandler es una interfaz encargada de capturar excepciones de tipo 403 FORBIDDEN.
    Estas excepciones son producidas cuando el usuario intenta acceder a un recurso privado, el sistema
    lo reconoce pero carece de permisos para su acceso
 */
public class CustomAccessDeniedHandler extends AuthenticationException implements AccessDeniedHandler {

    //Heredamos el constructor de la clase padre 'AuthenticationException'
    public CustomAccessDeniedHandler(HandlerExceptionResolver handlerExceptionResolver) {
        super(handlerExceptionResolver);
    }

    //Sobreescribimos el metodo handle() de la interfaz AccessDeniedHandler
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // En lugar de escribir el JSON aquí, delegamos la excepción al GlobalExceptionHandler
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}
