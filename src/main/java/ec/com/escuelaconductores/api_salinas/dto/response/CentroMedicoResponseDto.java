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
public class CentroMedicoResponseDto {

    private Long id;
    private Long idDistrito;
    private String nombreDistrito;
    private Long idProvincia;
    private String nombreProvincia;
    private Long idDepartamento;
    private String nombreDepartamento;
    private String ruc;
    private String nombre;
    private String direccion;
    private String autorizacion;
    private LocalDateTime fechaRegistro;
    private Integer estado;
}
