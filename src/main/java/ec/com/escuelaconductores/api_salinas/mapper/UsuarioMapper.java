package ec.com.escuelaconductores.api_salinas.mapper;


import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.UsuarioEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "idPersona", source = "persona.id")
    @Mapping(target = "nombrePersona",
            expression = "java(entity.getPersona().getNombres() + \" \" + entity.getPersona().getApellidos())")
    @Mapping(target = "idRol", source = "rol.id")
    @Mapping(target = "nombreRol", source = "rol.nombre")
    UsuarioResponseDto toResponse(UsuarioEntity entity);

    List<UsuarioResponseDto> toResponseList(List<UsuarioEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "clave", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    UsuarioEntity toEntity(UsuarioRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "clave", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void update(UsuarioRequestDto dto, @MappingTarget UsuarioEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "clave", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void patch(UsuarioRequestDto dto, @MappingTarget UsuarioEntity entity);
}
