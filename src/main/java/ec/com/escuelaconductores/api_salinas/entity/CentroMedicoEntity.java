package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "centro_medico")
@Entity(name = "CentroMedico")
public class CentroMedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_centro_medico")
    private Long id;
    private String ruc;
    private String nombre;
    private String direccion;
    private String autorizacion;
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;
    private Integer estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_distrito", nullable = false)
    private DistritoEntity distrito;

    @PrePersist
    void init() {
        if (this.autorizacion == null) {
            this.autorizacion = "C";
        }
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 1;
    }
}
