package com.example.api_rest_security.exceptions;

import com.example.api_rest_security.exceptions.response.ErrorResponse;
import com.example.api_rest_security.exceptions.service.GenerateErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @RestControllerAdvice: permite indicar que la clase sera un manejador global de excepciones. En este caso,
 *     todas las excepciones que se puedan producir seran atrapadas por este manejador.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Indicamos que este metodo unicamente capturara excepciones de tipo MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> methodArgumentNotValid(MethodArgumentNotValidException exception){
        /*
            Creamos un map con los errores por los cuales se produjo la excepcion. En este caso,
            esta excepcion atrapa los errores de validacion de campos de entrada de los usuario al
            momento de registrarse, por lo tanto, extrae el campo que no fue aceptado y su valor con el
            cual se envio. Es por ello, que se almacenan los campos fallidos en un Map
         */
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        /*
            Retornamos una respuesta con un formato personalizado por la clase ErrorResponse,
            dicho formato se genera a traves de la clase GenerateErrorResponse.
         */
        return new ResponseEntity<>(new GenerateErrorResponse<Map<String, String>>().generateErrorResponse(
                "Inputs are not valid", //Indicamos el mensaje que describe el tipo de error
                Map.of(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()), //Indicamos el codigo HTTP del error producido y su codigo
                errors, //Enviamos los campos invalidos por los cuales se ocasiono la excepcion
                LocalDateTime.now() //Enviamos la fecha actual del momento en que se producio la excepcion
        ), HttpStatus.BAD_REQUEST);
    }

}
