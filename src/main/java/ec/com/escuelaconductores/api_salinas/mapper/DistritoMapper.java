package ec.com.escuelaconductores.api_salinas.mapper;

import ec.com.escuelaconductores.api_salinas.dto.request.DistritoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DistritoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DistritoMapper {

    @Mapping(target = "idProvincia", source = "provincia.id")
    @Mapping(target = "nombreProvincia", source = "provincia.nombre")
    @Mapping(target = "idDepartamento", source = "provincia.departamento.id")
    @Mapping(target = "nombreDepartamento", source = "provincia.departamento.nombre")
    DistritoResponseDto toResponse(DistritoEntity entity);

    List<DistritoResponseDto> toResponseList(List<DistritoEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provincia", ignore = true)
    @Mapping(target = "estado", ignore = true)
    DistritoEntity toEntity(DistritoRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provincia", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(DistritoRequestDto dto, @MappingTarget DistritoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provincia", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(DistritoRequestDto dto, @MappingTarget DistritoEntity entity);
}
