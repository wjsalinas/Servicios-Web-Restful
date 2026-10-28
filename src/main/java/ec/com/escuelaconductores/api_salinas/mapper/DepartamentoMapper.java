package ec.com.escuelaconductores.api_salinas.mapper;


import ec.com.escuelaconductores.api_salinas.dto.request.DepartamentoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DepartamentoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DepartamentoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    DepartamentoResponseDto toResponse(DepartamentoEntity entity);

    List<DepartamentoResponseDto> toResponseList(List<DepartamentoEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    DepartamentoEntity toEntity(DepartamentoRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(DepartamentoRequestDto dto, @MappingTarget DepartamentoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(DepartamentoRequestDto dto, @MappingTarget DepartamentoEntity entity);
}
