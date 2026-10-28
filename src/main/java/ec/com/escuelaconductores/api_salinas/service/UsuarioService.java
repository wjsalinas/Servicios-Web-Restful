package ec.com.escuelaconductores.api_salinas.service;


import ec.com.escuelaconductores.api_salinas.dto.request.LoginRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.LoginResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDto> listar();

    UsuarioResponseDto obtener(Long id);

    UsuarioResponseDto crear(UsuarioRequestDto dto);

    UsuarioResponseDto actualizar(Long id, UsuarioRequestDto dto);

    UsuarioResponseDto actualizarParcial(Long id, UsuarioRequestDto dto);

    LoginResponseDto login(LoginRequestDto dto);

    void eliminar(Long id);

}
