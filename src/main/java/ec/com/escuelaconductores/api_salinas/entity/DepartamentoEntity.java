package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "departamento")
@Entity(name = "Departamento")
public class DepartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Long id;

    private String nombre;
    private Integer estado;

    @PrePersist
    void init() {
        this.estado = 1;
    }
}
