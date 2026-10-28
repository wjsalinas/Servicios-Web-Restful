package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroMedicoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroMedicoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.CentroMedicoEntity;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.CentroMedicoMapper;
import ec.com.escuelaconductores.api_salinas.repository.CentroMedicoRepository;
import ec.com.escuelaconductores.api_salinas.repository.DistritoRepository;
import ec.com.escuelaconductores.api_salinas.service.CentroMedicoService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CentroMedicoServiceImpl implements CentroMedicoService {

    private final CentroMedicoRepository centroMedicoRepository;
    private final DistritoRepository distritoRepository;
    private final CentroMedicoMapper centroMedicoMapper;

    @Override
    public List<CentroMedicoResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                            String nombreDepartamento, String nombreProvincia,
                                                            String nombreDistrito, String nombreEstablecimiento) {
        return centroMedicoMapper.toResponseList(
                centroMedicoRepository.buscarEstandar(idDepartamento, idProvincia, idDistrito,
                        nombreDepartamento, nombreProvincia, nombreDistrito, nombreEstablecimiento, Constantes.ACTIVO));
    }

    @Override
    public List<CentroMedicoResponseDto> listar() {
        return centroMedicoMapper.toResponseList(centroMedicoRepository.findByEstado(Constantes.ACTIVO));
    }

    @Override
    @Transactional
    public CentroMedicoResponseDto crear(CentroMedicoRequestDto dto) {
        validarRucDisponible(dto.getRuc(), null);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());
        CentroMedicoEntity entity = centroMedicoMapper.toEntity(dto);
        entity.setDistrito(distrito);
        return centroMedicoMapper.toResponse(centroMedicoRepository.save(entity));
    }

    @Override
    @Transactional
    public CentroMedicoResponseDto actualizar(Long id, CentroMedicoRequestDto dto) {
        CentroMedicoEntity entity = buscarActivo(id);
        validarRucDisponible(dto.getRuc(), id);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());
        centroMedicoMapper.update(dto, entity);
        entity.setDistrito(distrito);
        return centroMedicoMapper.toResponse(centroMedicoRepository.save(entity));
    }

    @Override
    @Transactional
    public CentroMedicoResponseDto actualizarParcial(Long id, CentroMedicoRequestDto dto) {
        CentroMedicoEntity entity = buscarActivo(id);
        if (dto.getRuc() != null) {
            validarRucDisponible(dto.getRuc(), id);
        }
        if (dto.getIdDistrito() != null) {
            entity.setDistrito(buscarDistrito(dto.getIdDistrito()));
        }
        centroMedicoMapper.patch(dto, entity);
        return centroMedicoMapper.toResponse(centroMedicoRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        CentroMedicoEntity entity = buscarActivo(id);
        entity.setEstado(Constantes.INACTIVO);
        centroMedicoRepository.save(entity);
    }

    private CentroMedicoEntity buscarActivo(Long id) {
        return centroMedicoRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Centro de evaluación", id));
    }

    private DistritoEntity buscarDistrito(Long idDistrito) {
        return distritoRepository.findByIdEstadoDist(idDistrito, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException(
                        "El distrito con id " + idDistrito + " no existe o está inactivo"));
    }

    private void validarRucDisponible(String ruc, Long idExcluido) {
        boolean existe = idExcluido == null
                ? centroMedicoRepository.existsByRuc(ruc)
                : centroMedicoRepository.existsByRucAndIdNot(ruc, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe un centro de evaluación con el RUC " + ruc);
        }
    }
}