package ec.com.escuelaconductores.api_salinas.service.impl;

import ec.com.escuelaconductores.api_salinas.dto.request.PersonaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.PersonaUsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioDatosRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaUsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.entity.PersonaEntity;
import ec.com.escuelaconductores.api_salinas.entity.RolEntity;
import ec.com.escuelaconductores.api_salinas.entity.UsuarioEntity;
import ec.com.escuelaconductores.api_salinas.exceptions.DataBusinessException;
import ec.com.escuelaconductores.api_salinas.exceptions.DataDuplicateException;
import ec.com.escuelaconductores.api_salinas.exceptions.ResourceNotFoundException;
import ec.com.escuelaconductores.api_salinas.mapper.PersonaMapper;
import ec.com.escuelaconductores.api_salinas.mapper.UsuarioMapper;
import ec.com.escuelaconductores.api_salinas.repository.PersonaRepository;
import ec.com.escuelaconductores.api_salinas.repository.RolRepository;
import ec.com.escuelaconductores.api_salinas.repository.UsuarioRepository;
import ec.com.escuelaconductores.api_salinas.security.Encrypt;
import ec.com.escuelaconductores.api_salinas.service.PersonaService;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PersonaMapper personaMapper;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<PersonaResponseDto> listar() {
        return personaMapper.toResponseList(
                personaRepository.findByEstadoOrderByApellidosAscNombresAsc(Constantes.ACTIVO));
    }

    @Override
    public PersonaResponseDto obtener(Long id) {
        return personaMapper.toResponse(buscarActivo(id));
    }


    @Override
    @Transactional
    public UsuarioResponseDto crearPersonaAdmin(UsuarioDatosRequestDto dto) {
        if (usuarioRepository.count() > 0) {
            throw new DataBusinessException("Ya existe un usuario administrador registrado en el sistema.");
        }
        PersonaEntity persona = personaRepository.findByTipoDocumentoAndNumeroDocumento("CI", "00000000")
                .orElseGet(() -> {
                    PersonaEntity nueva = new PersonaEntity();
                    nueva.setTipoDocumento("CI");
                    nueva.setNumeroDocumento("00000000");
                    nueva.setNombres("ADMINISTRADOR");
                    nueva.setApellidos("ADMINISTRADOR");
                    return personaRepository.save(nueva);
                });
        dto.setRol(Constantes.ROL_ADMIN);
        return crearUsuarioParaPersona(persona, dto.getUsuario(), dto.getClave(), dto.getRol());
    }

    @Override
    @Transactional
    public PersonaUsuarioResponseDto crear(PersonaUsuarioRequestDto dto) {
        validarDocumentoDisponible(dto.getPersona().getTipoDocumento(), dto.getPersona().getNumeroDocumento(), null);
        boolean tieneUsuario = dto.getUsuario().getUsuario() != null && !dto.getUsuario().getUsuario().isBlank();
        boolean tieneClave = dto.getUsuario().getClave() != null && !dto.getUsuario().getClave().isBlank();
        if (tieneUsuario != tieneClave) {
            throw new DataBusinessException("Para crear el usuario debe indicar el usuario y la clave juntos");
        }
        PersonaEntity entity = personaMapper.toEntity(dto.getPersona());
        entity = personaRepository.save(entity);
        UsuarioResponseDto usuarioCreado = null;
        if (tieneUsuario) {
            dto.getUsuario().setRol(Constantes.ROL_OPERADOR);
            usuarioCreado = crearUsuarioParaPersona(entity, dto.getUsuario().getUsuario(), dto.getUsuario().getClave(), dto.getUsuario().getRol());
        }
        return new PersonaUsuarioResponseDto(personaMapper.toResponse(entity), usuarioCreado);
    }

    private UsuarioResponseDto crearUsuarioParaPersona(PersonaEntity persona, String username, String password, String nameRol) {
        if (usuarioRepository.existsByUsuarioIgnoreCase(username)) {
            throw new DataDuplicateException("El usuario '" + username + "' ya está en uso");
        }
        RolEntity rol = rolRepository.findByNombreIgnoreCase(nameRol)
                .orElseThrow(() -> new DataBusinessException(
                        "No existe el rol por defecto " + nameRol + " para asignarlo al usuario"));
        String salt = Encrypt.generateSalt();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setPersona(persona);
        usuario.setRol(rol);
        usuario.setUsuario(username);
        usuario.setSalt(salt);
        usuario.setClave(Encrypt.hashPassword(password, salt));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public PersonaResponseDto actualizar(Long id, PersonaRequestDto dto) {
        PersonaEntity entity = buscarActivo(id);
        validarDocumentoDisponible(dto.getTipoDocumento(), dto.getNumeroDocumento(), id);
        personaMapper.update(dto, entity);
        return personaMapper.toResponse(personaRepository.save(entity));
    }

    @Override
    @Transactional
    public PersonaResponseDto actualizarParcial(Long id, PersonaRequestDto dto) {
        PersonaEntity entity = buscarActivo(id);
        String tipo = dto.getTipoDocumento() != null ? dto.getTipoDocumento() : entity.getTipoDocumento();
        String numero = dto.getNumeroDocumento() != null ? dto.getNumeroDocumento() : entity.getNumeroDocumento();
        validarDocumentoDisponible(tipo, numero, id);
        personaMapper.patch(dto, entity);
        return personaMapper.toResponse(personaRepository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        PersonaEntity entity = buscarActivo(id);
        if (usuarioRepository.existsByPersonaIdAndEstado(id, Constantes.ACTIVO)) {
            throw new DataBusinessException("No se puede eliminar la persona porque tiene un usuario activo");
        }
        entity.setEstado(Constantes.INACTIVO);
        personaRepository.save(entity);
    }

    private PersonaEntity buscarActivo(Long id) {
        return personaRepository.findByIdAndEstado(id, Constantes.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));
    }

    private void validarDocumentoDisponible(String tipo, String numero, Long idExcluido) {
        boolean existe = idExcluido == null
                ? personaRepository.existsByTipoDocumentoAndNumeroDocumento(tipo, numero)
                : personaRepository.existsByTipoDocumentoAndNumeroDocumentoAndIdNot(tipo, numero, idExcluido);
        if (existe) {
            throw new DataDuplicateException("Ya existe una persona con el documento " + tipo + " " + numero);
        }
    }
}
