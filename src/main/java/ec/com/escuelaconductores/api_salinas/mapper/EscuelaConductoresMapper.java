package ec.com.escuelaconductores.api_salinas.mapper;

import ec.com.escuelaconductores.api_salinas.dto.request.EscuelaConductoresRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.EscuelaConductoresResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.EscuelaConductoresEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EscuelaConductoresMapper {

    @Mapping(target = "idDistrito", source = "distrito.id")
    @Mapping(target = "nombreDistrito", source = "distrito.nombre")
    @Mapping(target = "idProvincia", source = "distrito.provincia.id")
    @Mapping(target = "nombreProvincia", source = "distrito.provincia.nombre")
    @Mapping(target = "idDepartamento", source = "distrito.provincia.departamento.id")
    @Mapping(target = "nombreDepartamento", source = "distrito.provincia.departamento.nombre")
    EscuelaConductoresResponseDto toResponse(EscuelaConductoresEntity entity);

    List<EscuelaConductoresResponseDto> toResponseList(List<EscuelaConductoresEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    EscuelaConductoresEntity toEntity(EscuelaConductoresRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(EscuelaConductoresRequestDto dto, @MappingTarget EscuelaConductoresEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(EscuelaConductoresRequestDto dto, @MappingTarget EscuelaConductoresEntity entity);
}
