package com.example.api_rest_security.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor //Indicamos la generacion automatica de un constructor con los atributos de la clase
@Data //Indicamos la generacion automatica de getters y setters para los atributos de la clase
public class ErrorResponse <T> {

    private String message;
    private Map<HttpStatus, Integer> typeError;
    //Marcamos como generico el contenido ya que no se determina con exactitud el error por el cual se produce la excepcion
    private T data;
    private LocalDateTime timestamp;

}
