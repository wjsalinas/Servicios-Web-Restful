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
public class PersonaRequestDto {

    @NotEmpty(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "RUC|CI|PASAPORTE", message = "El tipo de documento debe ser RUC, CI o PASAPORTE")
    private String tipoDocumento;

    @NotEmpty(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "El número de documento debe tener entre 10 y 15 caracteres alfanuméricos")
    private String numeroDocumento;

    @NotEmpty(message = "Los nombres son obligatorios")
    @Size(min = 1, max = 100, message = "Los nombres deben tener entre 1 y 100 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 1, max = 100, message = "El apellido debe tener entre 1 y 100 caracteres")
    private String apellidos;

    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 120, message = "El correo no puede superar los 120 caracteres")
    private String correo;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;
}
