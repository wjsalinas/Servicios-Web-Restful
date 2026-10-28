package ec.com.escuelaconductores.api_salinas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DistritoResponseDto {

    private Long id;
    private Long idProvincia;
    private String nombreProvincia;
    private Long idDepartamento;
    private String nombreDepartamento;
    private String nombre;
    private Integer estado;
}
