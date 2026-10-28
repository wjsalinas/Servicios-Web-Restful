package ec.com.escuelaconductores.api_salinas.mapper;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroEvaluacionRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.CentroEvaluacionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CentroEvaluacionMapper {

    @Mapping(target = "idDistrito", source = "distrito.id")
    @Mapping(target = "nombreDistrito", source = "distrito.nombre")
    @Mapping(target = "idProvincia", source = "distrito.provincia.id")
    @Mapping(target = "nombreProvincia", source = "distrito.provincia.nombre")
    @Mapping(target = "idDepartamento", source = "distrito.provincia.departamento.id")
    @Mapping(target = "nombreDepartamento", source = "distrito.provincia.departamento.nombre")
    CentroEvaluacionResponseDto toResponse(CentroEvaluacionEntity entity);

    List<CentroEvaluacionResponseDto> toResponseList(List<CentroEvaluacionEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    CentroEvaluacionEntity toEntity(CentroEvaluacionRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(CentroEvaluacionRequestDto dto, @MappingTarget CentroEvaluacionEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(CentroEvaluacionRequestDto dto, @MappingTarget CentroEvaluacionEntity entity);
}