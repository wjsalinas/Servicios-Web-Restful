package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.EscuelaConductoresRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.EscuelaConductoresResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.DistritoEntity;
import ec.com.escuelaconductores.api_salinas.entity.EscuelaConductoresEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.EscuelaConductoresMapper;
import ec.com.escuelaconductores.api_salinas.repository.DistritoRepository;
import ec.com.escuelaconductores.api_salinas.repository.EscuelaConductoresRepository;
import ec.com.escuelaconductores.api_salinas.service.EscuelaConductoresService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EscuelaConductoresServiceImpl implements EscuelaConductoresService {

    private final EscuelaConductoresRepository escuelaConductoresRepository;
    private final DistritoRepository distritoRepository;
    private final EscuelaConductoresMapper escuelaConductoresMapper;

    @Override
    public List<EscuelaConductoresResponseDto> buscarEstandar(Long idDepartamento, Long idProvincia, Long idDistrito,
                                                              String nombreDepartamento, String nombreProvincia,
                                                              String nombreDistrito, String nombreEstablecimiento) {
        return escuelaConductoresMapper.toResponseList(
                escuelaConductoresRepository.buscarEstandar(idDepartamento, idProvincia, idDistrito,
                        nombreDepartamento, nombreProvincia, nombreDistrito, nombreEstablecimiento));
    }

    @Override
    public List<EscuelaConductoresResponseDto> buscarRapida(String termino) {
        String valor = termino == null ? "" : termino.trim();
        return escuelaConductoresMapper.toResponseList(escuelaConductoresRepository.buscarRapido(valor));
    }

    @Override
    public Page<EscuelaConductoresResponseDto> listarPaginBd(Pageable pageable, String nombre) {
        String filtro = nombre == null ? "" : nombre.trim();
        return escuelaConductoresRepository
                .findByNombreEstadoEsc(filtro, Constantes.ACTIVO, pageable)
                .map(escuelaConductoresMapper::toResponse);
    }

    @Override
    public Page<EscuelaConductoresResponseDto> listarPaginMemoria(int page, int size, String nombre) {
        String filtro = (nombre == null || nombre.isBlank()) ? null : nombre.trim();
        List<EscuelaConductoresResponseDto> todas = escuelaConductoresMapper.toResponseList(
                escuelaConductoresRepository.buscarEstandar(null, null, null,
                        null, null, null, filtro));
        return paginarEnMemoria(todas, page, size, null);
    }

    @Override
    public Page<EscuelaConductoresResponseDto> listarPaginOrdenMemoria(int page, int size, String nombre,
                                                                       String field, String order, String field2, String order2) {
        String filtro = (nombre == null || nombre.isBlank()) ? null : nombre.trim();
        List<EscuelaConductoresResponseDto> todas = escuelaConductoresMapper.toResponseList(
                escuelaConductoresRepository.buscarEstandar(null, null, null,
                        null, null, null, filtro));
        Comparator<EscuelaConductoresResponseDto> comparador =
                construirComparador(field, order).thenComparing(construirComparador(field2, order2));
        List<EscuelaConductoresResponseDto> ordenadas = todas.stream().sorted(comparador).toList();
        Sort sort = Sort.by(Sort.Direction.fromString(order), field)
                .and(Sort.by(Sort.Direction.fromString(order2), field2));
        return paginarEnMemoria(ordenadas, page, size, sort);
    }


    private Page<EscuelaConductoresResponseDto> paginarEnMemoria(
            List<EscuelaConductoresResponseDto> lista, int page, int size, Sort sort) {
        int paginaIndex = Math.max(page - 1, 0);
        int tamanioPagina = Math.max(size, 1);
        Pageable pageable = sort == null
                ? PageRequest.of(paginaIndex, tamanioPagina)
                : PageRequest.of(paginaIndex, tamanioPagina, sort);
        int desde = (int) pageable.getOffset();
        if (desde >= lista.size()) {
            return new PageImpl<>(List.of(), pageable, lista.size());
        }
        int hasta = Math.min(desde + tamanioPagina, lista.size());
        return new PageImpl<>(lista.subList(desde, hasta), pageable, lista.size());
    }

    private Comparator<EscuelaConductoresResponseDto> construirComparador(String field, String order) {
        Comparator<EscuelaConductoresResponseDto> comparador = switch (field) {
            case "nombre" -> Comparator.comparing(EscuelaConductoresResponseDto::getNombre,
                    String.CASE_INSENSITIVE_ORDER);
            case "ruc" -> Comparator.comparing(EscuelaConductoresResponseDto::getRuc);
            case "direccion" -> Comparator.comparing(EscuelaConductoresResponseDto::getDireccion,
                    String.CASE_INSENSITIVE_ORDER);
            case "id" -> Comparator.comparing(EscuelaConductoresResponseDto::getId);
            default -> throw new DataBusinessException("No se puede ordenar por el campo '" + field + "'");
        };
        return "DESC".equalsIgnoreCase(order) ? comparador.reversed() : comparador;
    }

    @Override
    public EscuelaConductoresResponseDto obtener(Long id) {
        return escuelaConductoresMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public EscuelaConductoresResponseDto crear(EscuelaConductoresRequestDto dto) {
        validarRucDisponible(dto.getRuc(), null);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());
        EscuelaConductoresEntity entity = escuelaConductoresMapper.toEntity(dto);
        entity.setDistrito(distrito);
        return escuelaConductoresMapper.toResponse(escuelaConductoresRepository.save(entity));
    }

    @Override
    @Transactional
    public EscuelaConductoresResponseDto actualizar(Long id, EscuelaConductoresRequestDto dto) {
        EscuelaConductoresEntity entity = buscarActivo(id);
        validarRucDisponible(dto.getRuc(), id);
        DistritoEntity distrito = buscarDistrito(dto.getIdDistrito());

        escuelaConductoresMapper.update(dto, entity);
        entity.setDistrito(distrito);
        return escuelaConductoresMapper.toResponse(escuelaConductoresRepository.save(entity));
    }

    @Override
    @Transactional
    public EscuelaConductoresResponseDto actualizarParcial(Long id, EscuelaConductoresRequestDto dto) {
        EscuelaConductoresEntity entity = buscarActivo(id);

        if (dto.getRuc() != null) {
            validarRucDisponible(dto.getRuc(), id);
        }
        if (dto.getIdDistrito() != null) {
            entity.setDistrito(buscarDistrito(dto.getIdDistrito()));
        }

        escuelaConductoresMapper.patch(dto, entity);
        return escuelaConductoresMapper.toResponse(escuelaConductoresRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EscuelaConductoresEntity entity = buscarActivo(id);
        entity.setEstado(Constantes.INACTIVO);
        escuelaConductoresRepository.save(entity);
    }

    @Override
    @Transactional
    public EscuelaConductoresResponseDto activar(Long id) {
        EscuelaConductoresEntity entity = escuelaConductoresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escuela de conductores", id));
        if (Constantes.ACTIVO.equals(entity.getEstado())) {
            throw new DataBusinessException("La escuela de conductores ya está activa");
        }
        entity.setEstado(Constantes.ACTIVO);
        return escuelaConductoresMapper.toResponse(escuelaConductoresRepository.save(entity));
    }

    private EscuelaConductoresEntity buscarActivo(Long id) {
        return escuelaConductoresRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Escuela de conductores", id));
    }

    private DistritoEntity buscarDistrito(Long idDistrito) {
        return distritoRepository.findByIdEstadoDist(idDistrito, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException(
                        "El distrito con id " + idDistrito + " no existe o está inactivo"));
    }

    private void validarRucDisponible(String ruc, Long idExcluido) {
        boolean existe = idExcluido == null
                ? escuelaConductoresRepository.existsByRuc(ruc)
                : escuelaConductoresRepository.existsByRucAndIdNot(ruc, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe una escuela de conductores con el RUC " + ruc);
        }
    }
}
