package com.example.api_rest_security.service;

import com.example.api_rest_security.dtos.request.UserRegisterRequestDto;
import com.example.api_rest_security.dtos.response.UserResponseDto;
import com.example.api_rest_security.entity.User;
import com.example.api_rest_security.mapper.UserMapper;
import com.example.api_rest_security.repository.UserJpaRepository;
import com.example.api_rest_security.roles.Roles;

import lombok.AllArgsConstructor;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marcamos esta clase como un servicio que podra gestionar Spring
@AllArgsConstructor // Indicamos la generacion automatica de un constructor con todos los atributos
                    // de la clase
public class AuthenticationService {

    // Atributo requerido para guardar los usuarios en la base de datos
    private final UserJpaRepository userJpaRepository;

    // Atributo requerido para encriptar las claves de acceso de los usuarios
    private final PasswordEncoder passwordEncoder;

    // Atributo requerido para mapear DTOs a entidades y viceversa
    private final UserMapper userMapper;

    public UserResponseDto registerUser(UserRegisterRequestDto userRegisterRequestDto) {

        // Mapeamos el UserRegisterRequestDto a una entidad User
        User userStage = userMapper.toEntity(userRegisterRequestDto);
        // Le asignamos el rol de USER al usuario
        userStage.setRoles(Collections.singleton(Roles.USER));
        // Encriptamos la contraseña
        userStage.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        // Guardamos el usuario en la base de datos
        userJpaRepository.save(userStage);
        // Mapeamos la entidad a un DTO de respuesta y lo retornamos
        return userMapper.toResponseDto(userStage);
    }

}
