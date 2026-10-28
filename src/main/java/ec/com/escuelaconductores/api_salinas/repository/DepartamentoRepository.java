package ec.com.escuelaconductores.api_salinas.repository;

import ec.com.escuelaconductores.api_salinas.entity.DepartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, Long> {

    @Query(value = "Select d from Departamento d " +
            "where lower(d.nombre) like lower(concat('%', :nombre, '%')) " +
            "and d.estado= :estado order by d.id, d.nombre")
    List<DepartamentoEntity> findByNombreEstadoDept(@Param("nombre") String nombre,
                                                    @Param("estado") Integer estado);

    @Query(nativeQuery = true, value = "Select * from departamento where id_departamento = :id and estado= :estado")
    Optional<DepartamentoEntity> findByIdEstadoDept(@Param("id") Long id,
                                                    @Param("estado") Integer estado);

    boolean existsByIdAndEstado(Long id, Integer estado);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
