package ec.com.escuelaconductores.api_salinas.service;



import ec.com.escuelaconductores.api_salinas.dto.request.PersonaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.PersonaUsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioDatosRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaUsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;

import java.util.List;

public interface PersonaService {

    List<PersonaResponseDto> listar();

    PersonaResponseDto obtener(Long id);

    PersonaUsuarioResponseDto crear(PersonaUsuarioRequestDto dto);

    UsuarioResponseDto crearPersonaAdmin(UsuarioDatosRequestDto dto);

    PersonaResponseDto actualizar(Long id, PersonaRequestDto dto);

    PersonaResponseDto actualizarParcial(Long id, PersonaRequestDto dto);

    void eliminar(Long id);
}
