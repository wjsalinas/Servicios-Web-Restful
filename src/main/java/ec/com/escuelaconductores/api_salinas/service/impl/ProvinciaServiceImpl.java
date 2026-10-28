package ec.com.escuelaconductores.api_salinas.service.impl;


import ec.com.escuelaconductores.api_salinas.dto.request.ProvinciaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.ProvinciaResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DepartamentoEntity;
import ec.com.escuelaconductores.api_salinas.entity.ProvinciaEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.ProvinciaMapper;
import ec.com.escuelaconductores.api_salinas.repository.DepartamentoRepository;
import ec.com.escuelaconductores.api_salinas.repository.ProvinciaRepository;
import ec.com.escuelaconductores.api_salinas.service.ProvinciaService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProvinciaServiceImpl implements ProvinciaService {

    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ProvinciaMapper provinciaMapper;

    @Override
    public List<ProvinciaResponseDto> listar(Long idDepartamento, String nombre) {
        String filtro = nombre == null ? "" : nombre.trim();
        List<ProvinciaEntity> provincias;
        if (idDepartamento == null) {
            provincias = provinciaRepository.findByNombreEstadoProv(filtro, Constantes.ACTIVO);
        } else {
            if (!departamentoRepository.existsByIdAndEstado(idDepartamento, Constantes.ACTIVO)) {
                throw new ResourceNotFoundException("Departamento", idDepartamento);
            }
            provincias = provinciaRepository.findByIdNombreEstadoProv(idDepartamento, filtro, Constantes.ACTIVO);
        }
        return provinciaMapper.toResponseList(provincias);
    }

    @Override
    public ProvinciaResponseDto obtener(Long id) {
        return provinciaMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public ProvinciaResponseDto crear(ProvinciaRequestDto dto) {
        DepartamentoEntity departamento = buscarDepartamento(dto.getIdDepartamento());
        validarNombreDisponible(departamento.getId(), dto.getNombre(), null);
        ProvinciaEntity entity = provinciaMapper.toEntity(dto);
        entity.setDepartamento(departamento);
        return provinciaMapper.toResponse(provinciaRepository.save(entity));
    }

    @Override
    @Transactional
    public ProvinciaResponseDto actualizar(Long id, ProvinciaRequestDto dto) {
        ProvinciaEntity entity = buscarActivo(id);
        DepartamentoEntity departamento = buscarDepartamento(dto.getIdDepartamento());
        validarNombreDisponible(departamento.getId(), dto.getNombre(), id);
        provinciaMapper.update(dto, entity);
        entity.setDepartamento(departamento);
        return provinciaMapper.toResponse(provinciaRepository.save(entity));
    }

    @Override
    @Transactional
    public ProvinciaResponseDto actualizarParcial(Long id, ProvinciaRequestDto dto) {
        ProvinciaEntity entity = buscarActivo(id);
        DepartamentoEntity departamento = dto.getIdDepartamento() != null
                ? buscarDepartamento(dto.getIdDepartamento())
                : entity.getDepartamento();
        String nombre = dto.getNombre() != null ? dto.getNombre() : entity.getNombre();
        validarNombreDisponible(departamento.getId(), nombre, id);
        provinciaMapper.patch(dto, entity);
        entity.setDepartamento(departamento);
        return provinciaMapper.toResponse(provinciaRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ProvinciaEntity entity = buscarActivo(id);
        entity.setEstado(Constantes.INACTIVO);
        provinciaRepository.save(entity);
    }

    private ProvinciaEntity buscarActivo(Long id) {
        return provinciaRepository.findByIdEstadoProv(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Provincia", id));
    }

    private DepartamentoEntity buscarDepartamento(Long idDepartamento) {
        return departamentoRepository.findByIdEstadoDept(idDepartamento, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException(
                        "El departamento con id " + idDepartamento + " no existe o está inactivo"));
    }

    private void validarNombreDisponible(Long idDepartamento, String nombre, Long idExcluido) {
        boolean existe = idExcluido == null
                ? provinciaRepository.existsByDepartamentoIdAndNombreIgnoreCase(idDepartamento, nombre)
                : provinciaRepository.existsByDepartamentoIdAndNombreIgnoreCaseAndIdNot(idDepartamento, nombre, idExcluido);
        if (existe) {
            throw new DataDuplicateException(
                    "Ya existe la provincia '" + nombre + "' en el departamento indicado");
        }
    }
}
