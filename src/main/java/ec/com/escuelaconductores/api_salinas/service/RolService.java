package ec.com.escuelaconductores.api_salinas.service;


import ec.com.escuelaconductores.api_salinas.dto.request.RolRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.RolResponseDto;

import java.util.List;

public interface RolService {

    List<RolResponseDto> listar();

    RolResponseDto obtener(Long id);

    RolResponseDto crear(RolRequestDto dto);

    RolResponseDto actualizar(Long id, RolRequestDto dto);

    RolResponseDto actualizarParcial(Long id, RolRequestDto dto);

    void eliminar(Long id);
}
