package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.DistritoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DistritoResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import ec.com.escuelaconductores.api_salinas.entity.ProvinciaEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.DistritoMapper;
import ec.com.escuelaconductores.api_salinas.repository.DistritoRepository;
import ec.com.escuelaconductores.api_salinas.repository.EscuelaConductoresRepository;
import ec.com.escuelaconductores.api_salinas.repository.ProvinciaRepository;
import ec.com.escuelaconductores.api_salinas.service.DistritoService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistritoServiceImpl implements DistritoService {

    private final DistritoRepository distritoRepository;
    private final ProvinciaRepository provinciaRepository;
    private final EscuelaConductoresRepository escuelaConductoresRepository;
    private final DistritoMapper distritoMapper;

    @Override
    public List<DistritoResponseDto> listar(Long idProvincia, String nombre) {
        String filtro = nombre == null ? "" : nombre.trim();
        List<DistritoEntity> distritos;
        if (idProvincia == null) {
            distritos = distritoRepository.findByNombreEstadoDist(filtro, Constantes.ACTIVO);
        } else {
            if (!provinciaRepository.findByIdEstadoProv(idProvincia, Constantes.ACTIVO).isPresent()) {
                throw new ResourceNotFoundException("Provincia", idProvincia);
            }
            distritos = distritoRepository.findByIdNombreEstadoDist(idProvincia, filtro, Constantes.ACTIVO);
        }
        return distritoMapper.toResponseList(distritos);
    }

    @Override
    public DistritoResponseDto obtener(Long id) {
        return distritoMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public DistritoResponseDto crear(DistritoRequestDto dto) {
        ProvinciaEntity provincia = buscarProvincia(dto.getIdProvincia());
        validarNombreDisponible(provincia.getId(), dto.getNombre(), null);
        DistritoEntity entity = distritoMapper.toEntity(dto);
        entity.setProvincia(provincia);
        return distritoMapper.toResponse(distritoRepository.save(entity));
    }

    @Override
    @Transactional
    public DistritoResponseDto actualizar(Long id, DistritoRequestDto dto) {
        DistritoEntity entity = buscarActivo(id);
        ProvinciaEntity provincia = buscarProvincia(dto.getIdProvincia());
        validarNombreDisponible(provincia.getId(), dto.getNombre(), id);
        distritoMapper.update(dto, entity);
        entity.setProvincia(provincia);
        return distritoMapper.toResponse(distritoRepository.save(entity));
    }

    @Override
    @Transactional
    public DistritoResponseDto actualizarParcial(Long id, DistritoRequestDto dto) {
        DistritoEntity entity = buscarActivo(id);
        ProvinciaEntity provincia = dto.getIdProvincia() != null
                ? buscarProvincia(dto.getIdProvincia())
                : entity.getProvincia();
        String nombre = dto.getNombre() != null ? dto.getNombre() : entity.getNombre();
        validarNombreDisponible(provincia.getId(), nombre, id);
        distritoMapper.patch(dto, entity);
        entity.setProvincia(provincia);
        return distritoMapper.toResponse(distritoRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DistritoEntity entity = buscarActivo(id);
        boolean tieneEstablecimientos =
                escuelaConductoresRepository.existsByDistritoIdAndEstado(id, Constantes.ACTIVO);
        if (tieneEstablecimientos) {
            throw new DataBusinessException("No se puede eliminar el distrito porque tiene establecimientos activos");
        }
        entity.setEstado(Constantes.INACTIVO);
        distritoRepository.save(entity);
    }

    private DistritoEntity buscarActivo(Long id) {
        return distritoRepository.findByIdEstadoDist(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Distrito", id));
    }

    private ProvinciaEntity buscarProvincia(Long idProvincia) {
        return provinciaRepository.findByIdEstadoProv(idProvincia, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException(
                        "La provincia con id " + idProvincia + " no existe o está inactiva"));
    }

    private void validarNombreDisponible(Long idProvincia, String nombre, Long idExcluido) {
        boolean existe = idExcluido == null
                ? distritoRepository.existsByProvinciaIdAndNombreIgnoreCase(idProvincia, nombre)
                : distritoRepository.existsByProvinciaIdAndNombreIgnoreCaseAndIdNot(idProvincia, nombre, idExcluido);
        if (existe) {
            throw new DataDuplicateException(
                    "Ya existe el distrito '" + nombre + "' en la provincia indicada");
        }
    }
}
