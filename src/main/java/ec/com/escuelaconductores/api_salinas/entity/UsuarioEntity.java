package ec.com.escuelaconductores.api_salinas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "usuario")
@Entity(name = "Usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    private String usuario;
    private String clave;
    private String salt;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaRegistro;
    private Integer estado;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_persona", nullable = false, unique = true)
    private PersonaEntity persona;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;


    @PrePersist
    void init() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 1;
    }
}
