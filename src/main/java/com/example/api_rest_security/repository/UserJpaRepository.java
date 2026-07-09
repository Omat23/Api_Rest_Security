package com.example.api_rest_security.repository;

import com.example.api_rest_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Marcamos la clase como un repositorio para que sea manejada por Spring
//Extendemos de JpaRepository para poder usar los métodos de JPA y poder hacer consultas a la base de datos
public interface UserJpaRepository extends JpaRepository<User, Long>{

}
