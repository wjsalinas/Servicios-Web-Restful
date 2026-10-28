package ec.com.escuelaconductores.api_salinas.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonaUsuarioResponseDto {
    private PersonaResponseDto persona;
    private UsuarioResponseDto usuario;
}
