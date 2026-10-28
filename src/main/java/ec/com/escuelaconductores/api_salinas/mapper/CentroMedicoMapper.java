package ec.com.escuelaconductores.api_salinas.mapper;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroMedicoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroMedicoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.CentroMedicoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    @Mapping(target = "idDistrito", source = "distrito.id")
    @Mapping(target = "nombreDistrito", source = "distrito.nombre")
    @Mapping(target = "idProvincia", source = "distrito.provincia.id")
    @Mapping(target = "nombreProvincia", source = "distrito.provincia.nombre")
    @Mapping(target = "idDepartamento", source = "distrito.provincia.departamento.id")
    @Mapping(target = "nombreDepartamento", source = "distrito.provincia.departamento.nombre")
    CentroMedicoResponseDto toResponse(CentroMedicoEntity entity);

    List<CentroMedicoResponseDto> toResponseList(List<CentroMedicoEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    CentroMedicoEntity toEntity(CentroMedicoRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(CentroMedicoRequestDto dto, @MappingTarget CentroMedicoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distrito", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(CentroMedicoRequestDto dto, @MappingTarget CentroMedicoEntity entity);
}