package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "persona")
@Entity(name = "Persona")
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private Integer estado;

    @PrePersist
    void init() {
        this.estado = 1;
        this.fechaRegistro = LocalDateTime.now();
    }
}
