package ec.com.escuelaconductores.api_salinas.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonaUsuarioRequestDto {

    @Valid
    @NotNull(message = "Los datos de la persona son obligatorios")
    private PersonaRequestDto persona;

    private UsuarioDatosRequestDto usuario;
}
