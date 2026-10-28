package ec.com.escuelaconductores.api_salinas.service;



import ec.com.escuelaconductores.api_salinas.dto.request.DistritoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DistritoResponseDto;

import java.util.List;

public interface DistritoService {


    List<DistritoResponseDto> listar(Long idProvincia, String nombre);

    DistritoResponseDto obtener(Long id);

    DistritoResponseDto crear(DistritoRequestDto dto);

    DistritoResponseDto actualizar(Long id, DistritoRequestDto dto);

    DistritoResponseDto actualizarParcial(Long id, DistritoRequestDto dto);

    void eliminar(Long id);
}
