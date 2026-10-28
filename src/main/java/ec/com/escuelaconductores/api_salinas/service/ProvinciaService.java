package ec.com.escuelaconductores.api_salinas.service;

import ec.com.escuelaconductores.api_salinas.dto.request.ProvinciaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.ProvinciaResponseDto;

import java.util.List;

public interface ProvinciaService {


    List<ProvinciaResponseDto> listar(Long idDepartamento, String nombre);

    ProvinciaResponseDto obtener(Long id);

    ProvinciaResponseDto crear(ProvinciaRequestDto dto);

    ProvinciaResponseDto actualizar(Long id, ProvinciaRequestDto dto);

    ProvinciaResponseDto actualizarParcial(Long id, ProvinciaRequestDto dto);

    void eliminar(Long id);
}
