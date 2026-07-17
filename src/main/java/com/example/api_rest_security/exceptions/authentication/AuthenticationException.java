package com.example.api_rest_security.exceptions.authentication;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerExceptionResolver;

@AllArgsConstructor //Indicamos la generacion automatica de un constructor con todos los atributos de clase
public class AuthenticationException {

    /*
        Inyectamos un atributo de tipo HandlerExceptionResolver que funcionara como puente al GlobalExceptionHanlder
        para que capture la excepcion y la muestre
     */
    protected final HandlerExceptionResolver handlerExceptionResolver;

}
