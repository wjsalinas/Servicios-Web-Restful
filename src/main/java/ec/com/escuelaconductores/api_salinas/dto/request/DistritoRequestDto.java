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
public class DistritoRequestDto {

    @NotNull(message = "La provincia es obligatoria")
    @Min(value = 1, message = "El id de la provincia debe ser mayor a 0")
    private Long idProvincia;

    @NotEmpty(message = "El nombre del distrito es obligatorio")
    @Size(min = 1, max = 60, message = "El nombre debe tener entre 1 y 60 caracteres")
    private String nombre;
}
