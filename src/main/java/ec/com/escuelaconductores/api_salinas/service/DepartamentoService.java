package ec.com.escuelaconductores.api_salinas.service;


import ec.com.escuelaconductores.api_salinas.dto.request.DepartamentoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DepartamentoResponseDto;

import java.util.List;

public interface DepartamentoService {

    List<DepartamentoResponseDto> listar(String nombre);

    DepartamentoResponseDto obtener(Long id);

    DepartamentoResponseDto crear(DepartamentoRequestDto dto);

    DepartamentoResponseDto actualizar(Long id, DepartamentoRequestDto dto);

    DepartamentoResponseDto actualizarParcial(Long id, DepartamentoRequestDto dto);

    void eliminar(Long id);
}
