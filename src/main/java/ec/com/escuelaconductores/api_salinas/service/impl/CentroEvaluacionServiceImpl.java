package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroEvaluacionRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.CentroEvaluacionEntity;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.CentroEvaluacionMapper;
import ec.com.escuelaconductores.api_salinas.repository.CentroEvaluacionRepository;
import ec.com.escuelaconductores.api_salinas.repository.DistritoRepository;
import ec.com.escuelaconductores.api_salinas.service.CentroEvaluacionService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CentroEvaluacionServiceImpl implements CentroEvaluacionService {

    private final CentroEvaluacionRepository centroEvaluacionRepository;
    private final DistritoRepository distritoRepository;
    private final CentroEvaluacionMapper centroEvaluacionMapper;

    @Override
    public List<CentroEvaluacionResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                            String nombreDepartamento, String nombreProvincia,
                                                            String nombreDistrito, String nombreEstablecimiento) {
        return centroEvaluacionMapper.toResponseList(
                centroEvaluacionRepository.buscarEstandar(idDepartamento, idProvincia, idDistrito,
                        nombreDepartamento, nombreProvincia, nombreDistrito, nombreEstablecimiento, Constantes.ACTIVO));
    }

    @Override
    public List<CentroEvaluacionResponseDto> listar() {
        return centroEvaluacionMapper.toResponseList(centroEvaluacionRepository.findByEstado(Constantes.ACTIVO));
    }

    @Override
    @Transactional
    public CentroEvaluacionResponseDto crear(CentroEvaluacionRequestDto dto) {
        validarRucDisponible(dto.getRuc(), null);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());
        CentroEvaluacionEntity entity = centroEvaluacionMapper.toEntity(dto);
        entity.setDistrito(distrito);
        return centroEvaluacionMapper.toResponse(centroEvaluacionRepository.save(entity));
    }

    @Override
    @Transactional
    public CentroEvaluacionResponseDto actualizar(Long id, CentroEvaluacionRequestDto dto) {
        CentroEvaluacionEntity entity = buscarActivo(id);
        validarRucDisponible(dto.getRuc(), id);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());
        centroEvaluacionMapper.update(dto, entity);
        entity.setDistrito(distrito);
        return centroEvaluacionMapper.toResponse(centroEvaluacionRepository.save(entity));
    }

    @Override
    @Transactional
    public CentroEvaluacionResponseDto actualizarParcial(Long id, CentroEvaluacionRequestDto dto) {
        CentroEvaluacionEntity entity = buscarActivo(id);
        if (dto.getRuc() != null) {
            validarRucDisponible(dto.getRuc(), id);
        }
        if (dto.getIdDistrito() != null) {
            entity.setDistrito(buscarDistrito(dto.getIdDistrito()));
        }
        centroEvaluacionMapper.patch(dto, entity);
        return centroEvaluacionMapper.toResponse(centroEvaluacionRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        CentroEvaluacionEntity entity = buscarActivo(id);
        entity.setEstado(Constantes.INACTIVO);
        centroEvaluacionRepository.save(entity);
    }

    private CentroEvaluacionEntity buscarActivo(Long id) {
        return centroEvaluacionRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Centro de evaluación", id));
    }

    private DistritoEntity buscarDistrito(Long idDistrito) {
        return distritoRepository.findByIdEstadoDist(idDistrito, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException(
                        "El distrito con id " + idDistrito + " no existe o está inactivo"));
    }

    private void validarRucDisponible(String ruc, Long idExcluido) {
        boolean existe = idExcluido == null
                ? centroEvaluacionRepository.existsByRuc(ruc)
                : centroEvaluacionRepository.existsByRucAndIdNot(ruc, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe un centro de evaluación con el RUC " + ruc);
        }
    }
}