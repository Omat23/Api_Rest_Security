package com.example.api_rest_security.config;

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

@EnableWebSecurity
@Configuration // Marcamos la clase como una clase de configuracion y gestor de beans
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean // Marcamos el metodo como un bean que sera gestionado por Spring
    // Creamos el objeto Encoder para encriptar passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //Marcamos el metodo como un bean para ser gestionado por Spring
    /*
        Implementamos un metodo llamado SecurityFilterChain el cual acturara como el
        sistema de filtros que intercepta las peticiones HTTP provenientes del cliente
        antes de llegar a los controladores. En este metodo se pueden configurar rutas privadas
        o publicas, rutas a las cuales se requiere un permiso especial, manejo de sesiones, etc.
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //Indicamos las rutas publicas (libre acceso) y las que requieren de autenticacion previa para su acceso
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/register").permitAll();
                    auth.anyRequest().authenticated();
                })
                /*
                    Desactivamos la proteccion CSRF debido a que estamos trabajando en una api rest
                    la cual no manejara ni creara sesiones en un navegador web.
                 */
                .csrf(AbstractHttpConfigurer::disable)
                /*
                    Indicamos la politica de gestion de las sesiones que se creen, en este caso,
                    STATELESS indica que no se requiere de crear sesiones en el servidor
                 */
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
                return httpSecurity.build();
    }

    @Bean //Marcamos el metodo como un bean para ser gestionado por Spring
    /**
     *  Creamos un metodo que devuelve un DaoAuthenticationProvider. Este mismo es un proveedor de autenticacion
     *  de los cuales el AuthenticationManager delega las credenciales que envio el usuario para comenzar con el proceso
     *  de autenticacion. Imaginalo como un coordinador que indica como autenticar las credenciales enviadas.
     * @param CustomUserDetailsService: Recibe como parametro un CustomUserDetailsService el cual se encargara de ir a la base de datos para
     * comprobar si el usuario con las credenciales que se enviaron existe o no.
     * @return: Se retorna un objeto UserAuthenticationProvider que contiene el usuario ya autenticado.
     */
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService customUserDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        //Se le indica el algoritmo matematico para encriptar la clave entrante y compararla con la guardada en la base de datos
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean ////Marcamos el metodo como un bean para ser gestionado por Spring
    /**
     * Creamos un metodo que devuelve un proveedor de autenticacion.
     * @param DaoAuthenticationProvider: Indicamos el proveedor de autenticacion que se hara cargo de validar las peticiones HTTP
     * que lleguen al controlador
     * @return: Se retorna un ProviderManager con el proveedor de autenticacion indicado.
     */
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        return new ProviderManager(daoAuthenticationProvider);
    }
}
