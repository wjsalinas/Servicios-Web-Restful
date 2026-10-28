package ec.com.escuelaconductores.api_salinas.service;

import ec.com.escuelaconductores.api_salinas.dto.request.EscuelaConductoresRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.EscuelaConductoresResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EscuelaConductoresService {


    List<EscuelaConductoresResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                       String nombreDepartamento, String nombreProvincia,
                                                       String nombreDistrito, String nombreEstablecimiento);

    List<EscuelaConductoresResponseDto> buscarRapida(String termino);

    Page<EscuelaConductoresResponseDto> listarPaginBd(Pageable pageable, String nombre);

    Page<EscuelaConductoresResponseDto> listarPaginMemoria(int page, int size, String nombre);

    Page<EscuelaConductoresResponseDto> listarPaginOrdenMemoria(int page, int size, String nombre, String field,
                                                                String order, String field2, String order2);

    EscuelaConductoresResponseDto obtener(Long id);

    EscuelaConductoresResponseDto crear(EscuelaConductoresRequestDto dto);

    EscuelaConductoresResponseDto actualizar(Long id, EscuelaConductoresRequestDto dto);

    EscuelaConductoresResponseDto actualizarParcial(Long id, EscuelaConductoresRequestDto dto);

    EscuelaConductoresResponseDto activar(Long id);

    void eliminar(Long id);

}
