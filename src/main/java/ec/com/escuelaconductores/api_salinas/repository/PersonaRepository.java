package ec.com.escuelaconductores.api_salinas.repository;

import ec.com.escuelaconductores.api_salinas.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

    List<PersonaEntity> findByEstadoOrderByApellidosAscNombresAsc(Integer estado);

    Optional<PersonaEntity> findByIdAndEstado(Long id, Integer estado);

    Optional<PersonaEntity> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    boolean existsByTipoDocumentoAndNumeroDocumentoAndIdNot(String tipoDocumento, String numeroDocumento, Long id);
}
