package ec.com.escuelaconductores.api_salinas.dto.response;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDto {

    private boolean autenticado;
    private String mensaje;
    private String usuario;
    private String token;
    private String tipo;
    private Instant expiraEn;
}
