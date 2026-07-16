package com.example.api_rest_security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service // Marcamos la clase como un servicio gestionado por Spring
public class JwtService {

    @Value("${secret.key}") // Inyectamos la firma del token desde el .properties por medio de variables de
                            // entorno
    private String secretKey;

    /**
     * Metodo encargado de decodificar la firma del token para ser usada en la
     * encriptacion y desencriptacion de los mismos
     * 
     * @return Retorna una llave tipo Key lista para usar en el algoritmo HMAC
     */
    private Key getSecretKey() {
        byte[] decodeKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    // Metodo publico para acceder a la generacion de un token
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Metodo encargado de crear y estructurar un token JWT agregando detalles como
     * el payload, la firma
     * digital y el tiempo de expiracion.
     * 
     * @param extraClaims: Claims adicionales que se desean agregar al token.
     * @param userDetails: Objeto que contiene los detalles del usuario.
     * @return Retorna un token JWT listo para usar.
     */
    private String generateToken(HashMap<String, String> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .compact();
    }

    /**
     * Metodo para extraer el 'email' del token JWT
     * 
     * @param token: Token JWT enviado para su lectura
     * @return Retorna el email del usuario
     */
    public String extractEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Metodo para extraer la fecha de expiracion del token JWT
     * 
     * @param token: Token JWT enviado para su lectura
     * @return Retorna la fecha de expiracion del token
     */
    public Date extractExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Metodo generico para extraer un claim requerido del token
     * 
     * @param <T>:   Tipo de dato del claim a extraer (ya sea el email, fecha de
     *               expiracion, password, etc)
     * @param token: Token JWT enviado para su lectura
     * @param claim: Funcion para extraer el claim del token
     * @return Retorna el claim extraido del token
     */
    private <T> T extractClaim(String token, Function<Claims, T> claim) {
        Claims claims = extractAllClaims(token);
        return claim.apply(claims);
    }

    /**
     * Metodo para extraer todos los claims contenidos dentro del token JWT
     * 
     * @param token: Token JWT enviado para su lectura
     * @return Retorna un objeto Claims con toda la informacion contenida en el
     *         payload del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // Metodo para la lectura del token
                /*
                 * Metodo encargado de validar la firma del token de lectura entrante
                 * comparandolo
                 * con la clave de seguridad definida en el archivo .properties. Si coincide la
                 * firma
                 * del token con la clave privada, el token es valido y se puede proceder a
                 * extraer los claims.
                 */
                .setSigningKey(getSecretKey())
                .build() // Construye la instancia del parser
                .parseClaimsJws(token) // Metodo para extraer todos los claims del token
                .getBody(); // Retornamos el body del token que vendria siendo el payload con la informacion
                            // del usuario y la fecha de expiracion
    }

    /**
     * Metodo para validar autenticidad del token. Se valida el email extraido del
     * token enviado para autenticar
     * con el email del usuario traido de la db, junto con eso se valida si el token
     * aun no ha expirado.
     * 
     * @param userDetails: Objeto que contiene los detalles del usuario que se esta
     *                     autenticando
     * @param token:       Token JWT que contiene los detalles del usuario que se
     *                     quiere autenticar
     * @return Retorna true si el token es valido, false en caso contrario
     */
    public boolean isTokenValid(UserDetails userDetails, String token) {
        String username = extractEmailFromToken(token); // Se extrae el email del token
        // Se valida si el email es correcto y si el token no ha expirado
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Metodo para validar si el token ha expirado. Compara la fecha de expiracion
     * extraida del token
     * con la fecha actual.
     * 
     * @param token: Token JWT que se desea validar
     * @return Retorna true si el token ha expirado, false en caso contrario
     */
    private boolean isTokenExpired(String token) {
        // Retorna true si la fecha actual es mayor a la fecha de expiracion
        return extractExpirationDateFromToken(token).before(new Date());
    }

}
