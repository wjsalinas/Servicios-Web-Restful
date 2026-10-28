package ec.com.escuelaconductores.api_salinas.mapper;


import ec.com.escuelaconductores.api_salinas.dto.request.PersonaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.PersonaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaResponseDto toResponse(PersonaEntity entity);

    List<PersonaResponseDto> toResponseList(List<PersonaEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    PersonaEntity toEntity(PersonaRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(PersonaRequestDto dto, @MappingTarget PersonaEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(PersonaRequestDto dto, @MappingTarget PersonaEntity entity);
}
