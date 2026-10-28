package ec.com.escuelaconductores.api_salinas.repository;

import ec.com.escuelaconductores.api_salinas.entity.EscuelaConductoresEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EscuelaConductoresRepository extends JpaRepository<EscuelaConductoresEntity, Long> {

    Optional<EscuelaConductoresEntity> findByIdAndEstado(Long id, Integer estado);

    Optional<EscuelaConductoresEntity> findByRuc(String ruc);

    boolean existsByRuc(String ruc);

    boolean existsByRucAndIdNot(String ruc, Long id);

    boolean existsByDistritoIdAndEstado(Long idDistrito, Integer estado);


    @Query(value = "EXEC sp_buscar_escuela_conductores "
            + ":idDepartamento, :idProvincia, :idDistrito, :nombreDepartamento, :nombreProvincia, :nombreDistrito, :nombreEstablecimiento",
            nativeQuery = true)
    List<EscuelaConductoresEntity> buscarEstandar(@Param("idDepartamento") Long idDepartamento,
                                                  @Param("idProvincia") Long idProvincia,
                                                  @Param("idDistrito") Long idDistrito,
                                                  @Param("nombreDepartamento") String nombreDepartamento,
                                                  @Param("nombreProvincia") String nombreProvincia,
                                                  @Param("nombreDistrito") String nombreDistrito,
                                                  @Param("nombreEstablecimiento") String nombreEstablecimiento);

    @Query(value = "EXEC sp_buscar_rapido_escuela_conductores :termino", nativeQuery = true)
    List<EscuelaConductoresEntity> buscarRapido(@Param("termino") String termino);

    @Query(value = "Select e from Escuela e join fetch e.distrito d " +
            "where lower(e.nombre) like lower(concat('%', :nombre, '%')) " +
            "and e.estado = :estado",
            countQuery = "Select count(e) from Escuela e " +
                    "where lower(e.nombre) like lower(concat('%', :nombre, '%')) " +
                    "and e.estado = :estado")
    Page<EscuelaConductoresEntity> findByNombreEstadoEsc(@Param("nombre") String nombre,
                                                         @Param("estado") Integer estado,
                                                         Pageable pageable);
}
