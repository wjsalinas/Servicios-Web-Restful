package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "escuela_conductores")
@Entity(name = "Escuela")
public class EscuelaConductoresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escuela")
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
