package com.example.api_rest_security.service;

import com.example.api_rest_security.dtos.details.CustomUserDetails;
import com.example.api_rest_security.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marcamos la clase como un servicio que gestionara Spring
@AllArgsConstructor // Indicamos la generacion automatica de un constructor con todos los atributos
                    // de clase
public class CustomUserDetailsService implements UserDetailsService {

    // Inyectamos un objeto de tipo UserJpaRepository para acceder a nuestra base de
    // datos y consultar usuarios
    private final UserJpaRepository userJpaRepository;

    /**
     * Sobreescribimos el metodo loadUserByUsername de la interfaz
     * UserDetailsService.
     * Este metodo se encarga de obtener la informacion de un usuario de nuestra
     * base de datos
     * 
     * @param email: Indica el correo electronico del usuario que se desea obtener
     * @return: Devuelve un objeto de tipo CustomUserDetails con la informacion del
     *          usuario
     * @throws UsernameNotFoundException: Si el usuario no se encuentra en la base
     *                                    de datos
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)));
    }
}
