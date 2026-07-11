package com.example.api_rest_security.exceptions.service;

import com.example.api_rest_security.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

//Indicamos con un generico que esta clase podra recibir datos de cualquier tipo para enviar a la data del ErrorResponse
public class GenerateErrorResponse <T>{

    //Metodo que genera automaticamente un ErrorResponse solicitando los parametros requeridos
    public ErrorResponse<T> generateErrorResponse(String message,
                                                  Map<HttpStatus, Integer> typeError,
                                                  T data,
                                                  LocalDateTime timestamp) {
        return new ErrorResponse<>(message, typeError, data, timestamp);
    }

}
