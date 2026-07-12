package com.example.api_rest_security.dtos.details;

import com.example.api_rest_security.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor // INdicamos la generacion automatica de un constructor con los atributos de la
                    // clase
public class CustomUserDetails implements UserDetails {

    // Inyectamos un metodo de la entidad User para obtener la informacion del
    // usuario
    private final User user;

    /**
     * Sobreescribimos el metodo getAuthorities de la interfaz UserDetails.
     * Este metodo se encarga de obtener los roles del usuario
     * 
     * @return: Devuelve una coleccion de GrantedAuthority con los roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Sobreescribimos el metodo getPassword de la interfaz UserDetails.
     * Este metodo se encarga de obtener la contraseña del usuario
     * 
     * @return: Devuelve la contraseña del usuario
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Sobreescribimos el metodo getUsername de la interfaz UserDetails.
     * Este metodo se encarga de obtener el nombre de usuario
     * 
     * @return: Devuelve el nombre de usuario
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
