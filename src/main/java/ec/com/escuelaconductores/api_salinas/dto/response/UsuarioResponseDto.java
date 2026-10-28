package ec.com.escuelaconductores.api_salinas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioResponseDto {

    private Long id;
    private Long idPersona;
    private String nombrePersona;
    private Long idRol;
    private String nombreRol;
    private String usuario;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaRegistro;
    private Integer estado;
}
