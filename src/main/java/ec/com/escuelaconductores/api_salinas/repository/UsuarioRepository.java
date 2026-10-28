package ec.com.escuelaconductores.api_salinas.repository;

import ec.com.escuelaconductores.api_salinas.entity.UsuarioEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    List<UsuarioEntity> findByEstadoOrderByUsuario(Integer estado);

    Optional<UsuarioEntity> findByIdAndEstado(Long id, Integer estado);

    Optional<UsuarioEntity> findByUsuarioAndEstado(String username, Integer estado);

    boolean existsByUsuarioIgnoreCase(String username);

    boolean existsByUsuarioIgnoreCaseAndIdNot(String username, Long id);

    boolean existsByPersonaId(Long idPersona);

    boolean existsByPersonaIdAndEstado(Long idPersona, Integer estado);

    boolean existsByRolIdAndEstado(Long idRol, Integer estado);
}
