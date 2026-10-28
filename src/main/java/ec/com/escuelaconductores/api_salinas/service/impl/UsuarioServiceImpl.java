package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.LoginRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.LoginResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.PersonaEntity;
import ec.com.escuelaconductores.api_salinas.entity.RolEntity;
import ec.com.escuelaconductores.api_salinas.entity.UsuarioEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.UsuarioMapper;
import ec.com.escuelaconductores.api_salinas.repository.PersonaRepository;
import ec.com.escuelaconductores.api_salinas.repository.RolRepository;
import ec.com.escuelaconductores.api_salinas.repository.UsuarioRepository;
import ec.com.escuelaconductores.api_salinas.security.AuthenticationUser;
import ec.com.escuelaconductores.api_salinas.security.Encrypt;
import ec.com.escuelaconductores.api_salinas.security.JwtUtil;
import ec.com.escuelaconductores.api_salinas.service.UsuarioService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final AuthenticationUser authenticationUser;
    private final JwtUtil jwtUtil;

    @Override
    public List<UsuarioResponseDto> listar() {
        return usuarioMapper.toResponseList(usuarioRepository.findByEstadoOrderByUsuario(Constantes.ACTIVO));
    }

    @Override
    public UsuarioResponseDto obtener(Long id) {
        return usuarioMapper.toResponse(buscarActivo(id));
    }

    @Override
    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto dto) {
        validarUsernameDisponible(dto.getUsuario(), null);
        PersonaEntity persona = buscarPersona(dto.getIdPersona());
        RolEntity rol = buscarRol(dto.getIdRol());
        if (usuarioRepository.existsByPersonaId(persona.getId())) {
            throw new DataDuplicateException("La persona ya tiene un usuario asignado");
        }
        UsuarioEntity entity = usuarioMapper.toEntity(dto);
        entity.setPersona(persona);
        entity.setRol(rol);
        asignarClave(entity, dto.getClave());
        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    @Override
    @Transactional
    public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto dto) {
        UsuarioEntity entity = buscarActivo(id);
        validarUsernameDisponible(dto.getUsuario(), id);
        validarPersonaNoCambia(entity, dto.getIdPersona());
        entity.setRol(buscarRol(dto.getIdRol()));
        usuarioMapper.update(dto, entity);
        asignarClave(entity, dto.getClave());
        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    @Override
    @Transactional
    public UsuarioResponseDto actualizarParcial(Long id, UsuarioRequestDto dto) {
        UsuarioEntity entity = buscarActivo(id);

        if (dto.getUsuario() != null) {
            validarUsernameDisponible(dto.getUsuario(), id);
        }
        if (dto.getIdPersona() != null) {
            validarPersonaNoCambia(entity, dto.getIdPersona());
        }
        if (dto.getIdRol() != null) {
            entity.setRol(buscarRol(dto.getIdRol()));
        }
        usuarioMapper.patch(dto, entity);
        if (dto.getClave() != null) {
            asignarClave(entity, dto.getClave());
        }
        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        UsuarioEntity entity = buscarActivo(id);
        entity.setEstado(Constantes.INACTIVO);
        usuarioRepository.save(entity);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        LoginResponseDto response = new LoginResponseDto();
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(dto.getUsuario(), dto.getClave());
            Authentication authentication = authenticationUser.authenticate(request);
            UsuarioEntity usuario = usuarioRepository
                    .findByUsuarioAndEstado(authentication.getName(), Constantes.ACTIVO)
                    .orElseThrow(() -> new BadCredentialsException("Usuario o clave incorrectos."));
            String rol = usuario.getRol().getNombre();
            String token = jwtUtil.generar(usuario.getUsuario(), rol, usuario.getId());
            response.setAutenticado(true);
            response.setUsuario(usuario.getUsuario());
            response.setToken(token);
            response.setTipo("Bearer");
            response.setExpiraEn(jwtUtil.expiracionDe(token));
            response.setMensaje("Autenticación correcta.");
            return response;
        } catch (BadCredentialsException e) {
            response.setAutenticado(false);
            response.setUsuario(null);
            response.setToken(null);
            response.setMensaje(e.getMessage());
            return response;
        }
    }


    private UsuarioEntity buscarActivo(Long id) {
        return usuarioRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }

    private PersonaEntity buscarPersona(Long idPersona) {
        return personaRepository.findByIdAndEstado(idPersona, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException("La persona con id " + idPersona + " no existe o está inactiva"));
    }

    private RolEntity buscarRol(Long idRol) {
        return rolRepository.findByIdAndEstado(idRol, Constantes.ACTIVO)
                .orElseThrow(() -> new DataBusinessException("El rol con id " + idRol + " no existe o está inactivo"));
    }

    private void validarUsernameDisponible(String username, Long idExcluido) {
        boolean existe = idExcluido == null
                ? usuarioRepository.existsByUsuarioIgnoreCase(username)
                : usuarioRepository.existsByUsuarioIgnoreCaseAndIdNot(username, idExcluido);
        if (existe) {
            throw new DataDuplicateException("El usuario '" + username + "' ya está en uso");
        }
    }

    private void validarPersonaNoCambia(UsuarioEntity entity, Long idPersona) {
        if (!entity.getPersona().getId().equals(idPersona)) {
            throw new DataBusinessException("No se puede cambiar la persona asociada a un usuario");
        }
    }

    private void asignarClave(UsuarioEntity entity, String password) {
        String salt = Encrypt.generateSalt();
        entity.setSalt(salt);
        entity.setClave(Encrypt.hashPassword(password, salt));
    }
}
