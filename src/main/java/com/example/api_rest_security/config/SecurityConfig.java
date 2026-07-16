package com.example.api_rest_security.config;

import com.example.api_rest_security.jwt.JwtAuthenticationFilter;
import com.example.api_rest_security.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration // Marcamos la clase como una clase de configuracion y gestor de beans
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // Inyectamos nuestro filtro personalizado, en este caso el JwtAuthenticationFilter
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // Marcamos el metodo como un bean que sera gestionado por Spring
    // Creamos el objeto Encoder para encriptar passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Marcamos el metodo como un bean para ser gestionado por Spring
    /*
     * Implementamos un metodo llamado SecurityFilterChain el cual acturara como el
     * sistema de filtros que intercepta las peticiones HTTP provenientes del
     * cliente
     * antes de llegar a los controladores. En este metodo se pueden configurar
     * rutas privadas
     * o publicas, rutas a las cuales se requiere un permiso especial, manejo de
     * sesiones, etc.
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Indicamos las rutas publicas (libre acceso) y las que requieren de
                // autenticacion previa para su acceso
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/register", "/api/auth/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                /*
                 * Desactivamos la proteccion CSRF debido a que estamos trabajando en una api
                 * rest
                 * la cual no manejara ni creara sesiones en un navegador web.
                 */
                .csrf(AbstractHttpConfigurer::disable)
                /*
                 * Indicamos la politica de gestion de las sesiones que se creen, en este caso,
                 * STATELESS indica que no se requiere de crear sesiones en el servidor
                 */
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                // Añadimos nuestro filtro JWT justo antes del filtro UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
}
