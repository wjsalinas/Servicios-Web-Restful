package ec.com.escuelaconductores.api_salinas.mapper;


import ec.com.escuelaconductores.api_salinas.dto.request.ProvinciaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.ProvinciaResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.ProvinciaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProvinciaMapper {

    @Mapping(target = "idDepartamento", source = "departamento.id")
    @Mapping(target = "nombreDepartamento", source = "departamento.nombre")
    ProvinciaResponseDto toResponse(ProvinciaEntity entity);

    List<ProvinciaResponseDto> toResponseList(List<ProvinciaEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "estado", ignore = true)
    ProvinciaEntity toEntity(ProvinciaRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(ProvinciaRequestDto dto, @MappingTarget ProvinciaEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(ProvinciaRequestDto dto, @MappingTarget ProvinciaEntity entity);
}
