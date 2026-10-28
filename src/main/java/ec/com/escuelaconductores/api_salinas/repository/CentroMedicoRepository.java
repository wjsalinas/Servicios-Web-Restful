package ec.com.escuelaconductores.api_salinas.repository;

import ec.com.escuelaconductores.api_salinas.entity.CentroMedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedicoEntity, Long> {
    @Query("""
            select c from CentroMedico c
                join fetch c.distrito d
                join d.provincia p
                join p.departamento dep
            where c.estado = :estado
              and (:idDepartamento is null or dep.id = :idDepartamento)
              and (:idProvincia    is null or p.id   = :idProvincia)
              and (:idDistrito     is null or d.id   = :idDistrito)
              and (:nombreDepartamento    is null or lower(dep.nombre) like lower(concat('%', :nombreDepartamento, '%')))
              and (:nombreProvincia       is null or lower(p.nombre)   like lower(concat('%', :nombreProvincia, '%')))
              and (:nombreDistrito        is null or lower(d.nombre)   like lower(concat('%', :nombreDistrito, '%')))
              and (:nombreEstablecimiento is null or lower(c.nombre)   like lower(concat('%', :nombreEstablecimiento, '%')))
            order by dep.nombre, p.nombre, d.nombre, c.nombre
            """)
    List<CentroMedicoEntity> buscarEstandar(@Param("idDepartamento") Long idDepartamento,
                                                @Param("idProvincia") Long idProvincia,
                                                @Param("idDistrito") Long idDistrito,
                                                @Param("nombreDepartamento") String nombreDepartamento,
                                                @Param("nombreProvincia") String nombreProvincia,
                                                @Param("nombreDistrito") String nombreDistrito,
                                                @Param("nombreEstablecimiento") String nombreEstablecimiento,
                                                @Param("estado") Integer estado);

    List<CentroMedicoEntity> findByEstado(Integer estado);

    Optional<CentroMedicoEntity> findByIdAndEstado(Long id, Integer estado);

    boolean existsByRuc(String ruc);

    boolean existsByRucAndIdNot(String ruc, Long id);
}