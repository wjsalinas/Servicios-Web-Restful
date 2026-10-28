package ec.com.escuelaconductores.api_salinas.mapper;


import ec.com.escuelaconductores.api_salinas.dto.request.RolRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.RolResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.RolEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolResponseDto toResponse(RolEntity entity);

    List<RolResponseDto> toResponseList(List<RolEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    RolEntity toEntity(RolRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(RolRequestDto dto, @MappingTarget RolEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(RolRequestDto dto, @MappingTarget RolEntity entity);
}
