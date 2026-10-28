package ec.com.escuelaconductores.api_salinas.repository;
import ec.com.escuelaconductores.api_salinas.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Long> {

    @Query(value = "Select p from Provincia p " +
            "where lower(p.nombre) like lower(concat('%', :nombre, '%')) " +
            "and p.estado= :estado order by p.id, p.nombre")
    List<ProvinciaEntity> findByNombreEstadoProv(@Param("nombre") String nombre,
                                                 @Param("estado") Integer estado);

    @Query(value = "Select p from Provincia p " +
            "where p.departamento.id = :id and lower(p.nombre) like lower(concat('%', :nombre, '%')) " +
            "and p.estado= :estado order by p.id, p.nombre")
    List<ProvinciaEntity> findByIdNombreEstadoProv(@Param("id") Long id,
                                                   @Param("nombre") String nombre,
                                                   @Param("estado") Integer estado);

    @Query(nativeQuery = true, value = "Select * from provincia where id_provincia = :id and estado= :estado")
    Optional<ProvinciaEntity> findByIdEstadoProv(@Param("id") Long id,
                                                 @Param("estado") Integer estado);

    boolean existsByDepartamentoIdAndEstado(Long idDepartamento, Integer estado);

    boolean existsByDepartamentoIdAndNombreIgnoreCase(Long idDepartamento, String nombre);

    boolean existsByDepartamentoIdAndNombreIgnoreCaseAndIdNot(Long idDepartamento, String nombre, Long id);
}
