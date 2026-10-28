package ec.com.escuelaconductores.api_salinas.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CentroMedicoRequestDto {

    @NotNull(message = "El distrito es obligatorio")
    @Min(value = 1, message = "El id del distrito debe ser mayor a 0")
    private Long idDistrito;

    @NotEmpty(message = "El RUC es obligatorio")
    @Pattern(regexp = "^[0-9]{13}$", message = "El RUC debe tener 13 dígitos numéricos")
    private String ruc;

    @NotEmpty(message = "El nombre del establecimiento es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String nombre;

    @NotEmpty(message = "La dirección es obligatoria")
    @Size(min = 3, max = 250, message = "La dirección debe tener entre 3 y 250 caracteres")
    private String direccion;

    @Pattern(regexp = "C|S", message = "La autorización debe ser 'C' (con autorización) o 'S' (sin autorización)")
    private String autorizacion;
}
