package ec.com.escuelaconductores.api_salinas.service;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroEvaluacionRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;

import java.util.List;

public interface CentroEvaluacionService {

    List<CentroEvaluacionResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                     String nombreDepartamento, String nombreProvincia,
                                                     String nombreDistrito, String nombreEstablecimiento);

    List<CentroEvaluacionResponseDto> listar();

    CentroEvaluacionResponseDto crear(CentroEvaluacionRequestDto dto);

    CentroEvaluacionResponseDto actualizar(Long id, CentroEvaluacionRequestDto dto);

    CentroEvaluacionResponseDto actualizarParcial(Long id, CentroEvaluacionRequestDto dto);

    void eliminar(Long id);
}