package com.example.api_rest_security.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //Indicamos la generacion automatica de un constructor con todos los atributos de la clase
@NoArgsConstructor //Indicamos la generacion automatica de un constructror sin atributos
@Data //Indicamos la generacion automatica de getters y setters
public class UserResponseDto {

    private String username;
    private String surname;
    private String email;

}
