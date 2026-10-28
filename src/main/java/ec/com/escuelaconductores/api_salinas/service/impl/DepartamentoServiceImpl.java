package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.DepartamentoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DepartamentoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DepartamentoEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.DepartamentoMapper;
import ec.com.escuelaconductores.api_salinas.repository.DepartamentoRepository;
import ec.com.escuelaconductores.api_salinas.repository.ProvinciaRepository;
import ec.com.escuelaconductores.api_salinas.service.DepartamentoService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoMapper departamentoMapper;

    @Override
    public List<DepartamentoResponseDto> listar(String nombre) {
        String filtro = nombre == null ? "" : nombre.trim();
        return departamentoMapper.toResponseList(
                departamentoRepository.findByNombreEstadoDept(filtro, Constantes.ACTIVO));
    }

    @Override
    public DepartamentoResponseDto obtener(Long id) {
        return departamentoMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public DepartamentoResponseDto crear(DepartamentoRequestDto dto) {
        validarNombreDisponible(dto.getNombre(), null);
        DepartamentoEntity entity = departamentoMapper.toEntity(dto);
        return departamentoMapper.toResponse(departamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public DepartamentoResponseDto actualizar(Long id, DepartamentoRequestDto dto) {
        DepartamentoEntity entity = buscarActivo(id);
        validarNombreDisponible(dto.getNombre(), id);
        departamentoMapper.update(dto, entity);
        return departamentoMapper.toResponse(departamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public DepartamentoResponseDto actualizarParcial(Long id, DepartamentoRequestDto dto) {
        DepartamentoEntity entity = buscarActivo(id);
        if (dto.getNombre() != null) {
            validarNombreDisponible(dto.getNombre(), id);
        }
        departamentoMapper.patch(dto, entity);
        return departamentoMapper.toResponse(departamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DepartamentoEntity entity = buscarActivo(id);
        if (provinciaRepository.existsByDepartamentoIdAndEstado(id, Constantes.ACTIVO)) {
            throw new DataBusinessException("No se puede eliminar el departamento porque tiene provincias activas");
        }
        entity.setEstado(Constantes.INACTIVO);
        departamentoRepository.save(entity);
    }

    private DepartamentoEntity buscarActivo(Long id) {
        return departamentoRepository.findByIdEstadoDept(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento", id));
    }

    private void validarNombreDisponible(String nombre, Long idExcluido) {
        boolean existe = idExcluido == null
                ? departamentoRepository.existsByNombreIgnoreCase(nombre)
                : departamentoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe un departamento con el nombre '" + nombre + "'");
        }
    }
}
