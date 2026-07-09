package com.example.api_rest_security.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //Indicamos la generacion de un constructor con todos los atributos de la clase
@NoArgsConstructor //Indicamos la generacion de un constructor sin ningun atributo de la clase
@Data //Indicamos la generacion automatica de getters y setters
public class UserRequestDto {

    private String username;
    private String surname;
    private int age;
    private String email;
    private String password;

}
