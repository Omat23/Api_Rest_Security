package com.example.api_rest_security.jwt;

import com.example.api_rest_security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //Marcamos la clase como un componente capaz de ser gestionado por Spring
@AllArgsConstructor //Indicamos la generacion automatica de un constructor con los atributos de la clase
/*
    Extendemos de la clase OncePerRequestFilter para garantizar matemáticamente que el código
    que programes en su interior solo se ejecute una única vez por cada petición HTTP entrante hacia tu servidor.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Inyectamos este atributo para hacer uso de los metodos de gestion de token que proporciona la clase
    private final JwtService jwtService;

    //Inyectamos este atributo para comunicarnos con nuestra base de datos para consultar usuarios
    private final CustomUserDetailsService customUserDetailsService;

    //Implementamos el metodo de la clase OncePerRequestFilter para agregar la logica de filtracion de nuestro Jwt
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Extraemos la cabecera de la peticion HTTP con nombre 'Authorization'
        final String authorizationHeader = request.getHeader("Authorization");

        //Verificamos que la cabecera no sea nula y si inicia con el estandar industrial 'Bearer '
        /*
            Recordemos lo siguiente:

            - Authorization: Indica que la peticion HTTP incluye credenciales
            - Bearer : Es el esquema que le dice al servidor que quien posee ese token tiene acceso al recurso

            Juntos forman el estandar 'Authorization Bearer   <token>'
         */
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            //Extraemos el token puro recortando la palabra 'Bearer '
            final String tokenJwt = authorizationHeader.substring(7);

            //Extraemos el email del usuario propietario del token
            final String emailUser = jwtService.extractEmailFromToken(tokenJwt);

            //Verificamos que el email del usuario no sea nulo y no este registrado en el contexto actual de Spring Security
            if(emailUser != null && SecurityContextHolder.getContext().getAuthentication() == null){

                //Buscamos el usuario en la base de datos para verificar que siga existiendo y este activo
                final UserDetails userDetails = customUserDetailsService.loadUserByUsername(emailUser);

                //Validamos el token para verificar si le pertenece al usuario
                if(jwtService.isTokenValid(userDetails, tokenJwt)){

                    //Creamos un usuario marcado como autenticado para que sea reconocido en el contexto actual de Spring
                    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
                            emailUser,
                            null,
                            userDetails.getAuthorities()
                    );

                    //Agregamos contenido extra a la sesion
                    userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Inyectamos este usuario autenticado en el contexto actual de Spring
                    SecurityContextHolder.getContext().setAuthentication(userAuth);
                }
            }

            //Indicamos que al estar preparado ya el usuario, la peticion siga su flujo correspondiente a traves del sistema
            filterChain.doFilter(request, response);

        }else{
            //En caso de que el encabezado de la peticion HTTP sea nulo y no inicie con la palabra 'Bearer '
            //Dejamos que la peticion siga su flujo normal llegando a los demas filtros que impediran su paso
            filterChain.doFilter(request, response);
            return;
        }
    }
}
