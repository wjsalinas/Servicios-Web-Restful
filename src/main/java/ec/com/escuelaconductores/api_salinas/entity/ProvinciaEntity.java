package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "provincia")
@Entity(name = "Provincia")
public class ProvinciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Long id;

    private String nombre;
    private Integer estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_departamento", nullable = false)
    private DepartamentoEntity departamento;

    @PrePersist
    void init() {
        this.estado = 1;
    }
}
