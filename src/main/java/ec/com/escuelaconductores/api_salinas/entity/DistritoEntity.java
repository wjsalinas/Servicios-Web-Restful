package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "distrito")
@Entity(name = "Distrito")
public class DistritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distrito")
    private Long id;

    private String nombre;
    private Integer estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_provincia", nullable = false)
    private ProvinciaEntity provincia;

    @PrePersist
    void init() {
        this.estado = 1;
    }
}
