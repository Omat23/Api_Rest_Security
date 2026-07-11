package com.example.api_rest_security.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Indicamos la generacion de un constructor con todos los atributos de la clase
@NoArgsConstructor // Indicamos la generacion de un constructor sin ningun atributo de la clase
@Data // Indicamos la generacion automatica de getters y setters
public class UserRegisterRequestDto {

    /**
     * @NotBlank: Valida que el nombre de usuario no sea nulo ni consista solo de
     *            espacios en blanco.
     * @Pattern: Valida que el nombre de usuario solo contenga lo indicado en la
     *           expresion regular (REGEX).
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Pattern(regexp = "[A-Za-z ]+", message = "El nombre de usuario solo puede contener letras y espacios")
    private String username;

    /**
     * @NotBlank: Valida que el apellido no sea nulo ni consista únicamente de
     *            espacios en blanco.
     * @Pattern: Valida que el apellido solo contenga lo indicado en la expresion
     *           regular (REGEX).
     */
    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(regexp = "[A-Za-z ]+", message = "El apellido solo puede contener letras y espacios")
    private String surname;

    /**
     * @Min: Valida que el valor numérico no sea menor al mínimo establecido (en
     *       este caso, 18 años).
     * @Max: Valida que el valor numérico no sea mayor al máximo establecido (en
     *       este caso, 99 años).
     */
    @Min(value = 18, message = "La edad mínima permitida es 18 años")
    @Max(value = 99, message = "La edad máxima permitida es 99 años")
    private int age;

    /**
     * @NotBlank: Asegura que el correo no esté vacío.
     * @Email: Valida que el formato del texto ingresado coincida con el patrón de
     *         una dirección de correo electrónico válida.
     */
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    /**
     * @NotBlank: Valida que la contraseña no sea nula ni vacía.
     * @Size: Exige una longitud mínima para la contraseña (mínimo 6 caracteres) por
     *        cuestiones de seguridad.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

}
