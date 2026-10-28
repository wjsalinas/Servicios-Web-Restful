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
public class DepartamentoResponseDto {

    private Long id;
    private String nombre;
    private Integer estado;
}
