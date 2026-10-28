package ec.com.escuelaconductores.api_salinas.service;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroMedicoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroMedicoResponseDto;

import java.util.List;

public interface CentroMedicoService {

    List<CentroMedicoResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                     String nombreDepartamento, String nombreProvincia,
                                                     String nombreDistrito, String nombreEstablecimiento);

    List<CentroMedicoResponseDto> listar();

    CentroMedicoResponseDto crear(CentroMedicoRequestDto dto);

    CentroMedicoResponseDto actualizar(Long id, CentroMedicoRequestDto dto);

    CentroMedicoResponseDto actualizarParcial(Long id, CentroMedicoRequestDto dto);

    void eliminar(Long id);
}