package ec.com.escuelaconductores.api_salinas.dto.request;

import jakarta.validation.constraints.*;
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
public class ProvinciaRequestDto {

    @NotNull(message = "El departamento es obligatorio")
    @Min(value = 1, message = "El id del departamento debe ser mayor a 0")
    private Long idDepartamento;

    @NotEmpty(message = "El nombre de la provincia es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;
}
