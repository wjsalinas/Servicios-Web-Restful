package ec.com.escuelaconductores.api_salinas.service.impl;


import ec.com.escuelaconductores.api_salinas.dto.request.RolRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.RolResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.RolEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.RolMapper;
import ec.com.escuelaconductores.api_salinas.repository.RolRepository;
import ec.com.escuelaconductores.api_salinas.repository.UsuarioRepository;
import ec.com.escuelaconductores.api_salinas.service.RolService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolMapper rolMapper;

    @Override
    public List<RolResponseDto> listar() {
        return rolMapper.toResponseList(rolRepository.findByEstadoOrderByNombre(Constantes.ACTIVO));
    }

    @Override
    public RolResponseDto obtener(Long id) {
        return rolMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public RolResponseDto crear(RolRequestDto dto) {
        validarNombreDisponible(dto.getNombre(), null);
        RolEntity entity = rolMapper.toEntity(dto);
        return rolMapper.toResponse(rolRepository.save(entity));
    }

    @Override
    @Transactional
    public RolResponseDto actualizar(Long id, RolRequestDto dto) {
        RolEntity entity = buscarActivo(id);
        validarNombreDisponible(dto.getNombre(), id);
        rolMapper.update(dto, entity);
        return rolMapper.toResponse(rolRepository.save(entity));
    }

    @Override
    @Transactional
    public RolResponseDto actualizarParcial(Long id, RolRequestDto dto) {
        RolEntity entity = buscarActivo(id);
        if (dto.getNombre() != null) {
            validarNombreDisponible(dto.getNombre(), id);
        }
        rolMapper.patch(dto, entity);
        return rolMapper.toResponse(rolRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        RolEntity entity = buscarActivo(id);
        if (usuarioRepository.existsByRolIdAndEstado(id, Constantes.ACTIVO)) {
            throw new DataBusinessException("No se puede eliminar el rol porque tiene usuarios activos");
        }
        entity.setEstado(Constantes.INACTIVO);
        rolRepository.save(entity);
    }

    private RolEntity buscarActivo(Long id) {
        return rolRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
    }

    private void validarNombreDisponible(String nombre, Long idExcluido) {
        boolean existe = idExcluido == null
                ? rolRepository.existsByNombreIgnoreCase(nombre)
                : rolRepository.existsByNombreIgnoreCaseAndIdNot(nombre, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe un rol con el nombre '" + nombre + "'");
        }
    }
}
