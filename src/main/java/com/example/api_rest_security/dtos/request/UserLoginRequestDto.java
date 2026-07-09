package com.example.api_rest_security.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //Generamos automaticamente un constructor con todos los atributos de la clase
@NoArgsConstructor //Generamos automaticamente un constructor sin atributos
@Data //Generamos automaticamente los getters y setters de la clase
public class UserLoginRequestDto {

    private String email;
    private String password;

}
