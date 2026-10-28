package ec.com.escuelaconductores.api_salinas.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequestDto {

    @NotEmpty(message = "El usuario es obligatorio")
    @Size(min = 1, max = 50, message = "El usuario debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "El usuario solo admite letras, números, punto, guion y guion bajo")
    private String usuario;

    @NotEmpty(message = "La clave es obligatoria")
    @Size(min = 8, max = 100, message = "La clave debe tener entre 8 y 100 caracteres")
    private String clave;
}
