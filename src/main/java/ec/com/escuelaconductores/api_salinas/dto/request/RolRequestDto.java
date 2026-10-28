package ec.com.escuelaconductores.api_salinas.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class RolRequestDto {

    @NotEmpty(message = "El nombre del rol es obligatorio")
    @Size(min = 1, max = 30, message = "El nombre del rol debe tener entre 1 y 30 caracteres")
    @Pattern(regexp = "^[A-Za-z_]+$", message = "El nombre del rol solo admite letras y guion bajo")
    private String nombre;

    @Size(max = 120, message = "La descripción no puede superar los 120 caracteres")
    private String descripcion;
}
