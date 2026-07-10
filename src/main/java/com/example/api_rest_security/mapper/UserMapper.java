package com.example.api_rest_security.mapper;

import org.mapstruct.Mapper;

import com.example.api_rest_security.dtos.request.UserRegisterRequestDto;
import com.example.api_rest_security.dtos.response.UserResponseDto;
import com.example.api_rest_security.entity.User;

@Mapper(componentModel = "spring") // Marcamos la interfaz como un mapper y como componente gestionado por Spring
/*
 * Un Mapper es una clase encargada de mapear objetos de un tipo a otro, por
 * ejemplo,
 * de un DTO a una entidad o viceversa. Comunmente se hace uso de una interfaz
 * debido a
 * que lo unico que se requiere es definir los metodos de mapeo y no es
 * necesario
 * implementar logica en los mismos.
 */
public interface UserMapper {

    /**
     * Metodo encargado de convertir un objeto UserRegisterRequestDto en un objeto
     * User.
     * 
     * @param userRegisterRequestDto Objeto que contiene los datos del usuario a
     *                               convertir.
     * @return Objeto User con los datos del usuario convertido.
     */
    User toEntity(UserRegisterRequestDto userRegisterRequestDto);

    /**
     * Metodo encargado de convertir un objeto User en un objeto UserResponseDto.
     * 
     * @param user Objeto que contiene los datos del usuario a convertir.
     * @return Objeto UserResponseDto con los datos del usuario convertido.
     */
    UserResponseDto toResponseDto(User user);

}
