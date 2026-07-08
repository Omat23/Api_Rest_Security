package com.example.api_rest_security.entity;

import com.example.api_rest_security.roles.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity //Marcamos la clase como una entidad que manejara JPA
@Table(name = "users") //Indicamos el nombre de la tabla que hara referencia a esta entidad en la base de datos
@NoArgsConstructor //Indicamos la generacion automatica de un constructor vacio
@Data //Indicamos la generacion automatica de getters y setters
public class User {

    @Id //Marcamos el atributo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indicamos que el valor de la clave primaria se generara automaticamente por la base de datos
    private int id;

    @Column(nullable = false) //Indicamos que el atributo no puede ser nulo en la base de datos
    private String username;

    @Column(nullable = false) //Indicamos que el atributo no puede ser nulo en la base de datos
    private String surname;

    @Column(nullable = false) //Indicamos que el atributo no puede ser nulo en la base de datos
    private int age;

    @Column(nullable = false, unique = true) //Indicamos que el atributo no puede ser nulo en la base de datos y que debe ser unico
    private String email;

    @Column(nullable = false) //Indicamos que el atributo no puede ser nulo en la base de datos
    private String password;

    /*
        Indicamos que el atributo es una coleccion de elementos
        los cuales deberan cargarse de forma automatica cuando se solicite
     */
    @ElementCollection(fetch = FetchType.EAGER)
    /*
        Indicamos que los roles del usuario se guardaran en una tabla aparte llamada user_roles
        y que la columna que hara referencia a la clave primaria de la entidad User sera user_id
     */
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(nullable = false) //Indicamos que el atributo no puede ser nulo en la base de datos
    private Set<Roles> roles;

    //Generamos un constructor sin solicitar el id ni el set de roles ya que estos se generaran automaticamente por el sistema
    public User(String username, String surname, int age, String email, String password){
        this.username = username;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
    }

}
