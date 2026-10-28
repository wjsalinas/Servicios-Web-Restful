package ec.com.escuelaconductores.api_salinas.repository;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistritoRepository extends JpaRepository<DistritoEntity, Long> {

    @Query(value = "Select d from Distrito d " +
            "where lower(d.nombre) like lower(concat('%', :nombre, '%')) " +
            "and d.estado= :estado order by d.id, d.nombre")
    List<DistritoEntity> findByNombreEstadoDist(@Param("nombre") String nombre,
                                                @Param("estado") Integer estado);

    @Query(value = "Select d from Distrito d " +
            "where d.provincia.id = :id and lower(d.nombre) like lower(concat('%', :nombre, '%')) " +
            "and d.estado= :estado order by d.id, d.nombre")
    List<DistritoEntity> findByIdNombreEstadoDist(@Param("id") Long id,
                                                  @Param("nombre") String nombre,
                                                  @Param("estado") Integer estado);

    @Query(nativeQuery = true, value = "Select * from distrito where id_distrito = :id and estado= :estado")
    Optional<DistritoEntity> findByIdEstadoDist(@Param("id") Long id,
                                                @Param("estado") Integer estado);

    boolean existsByProvinciaIdAndEstado(Long idProvincia, Integer estado);

    boolean existsByProvinciaIdAndNombreIgnoreCase(Long idProvincia, String nombre);

    boolean existsByProvinciaIdAndNombreIgnoreCaseAndIdNot(Long idProvincia, String nombre, Long id);
}
