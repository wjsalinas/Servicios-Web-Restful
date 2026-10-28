package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.LoginRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.LoginResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;

@RestController
@RequestMapping(API + "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping()
    public List<UsuarioResponseDto> listar() {
        return usuarioService.listar();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crear(@Valid @RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto creado = usuarioService.crear(dto);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(creado.getId())
                        .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto obtener(@PathVariable Long id) {
        return usuarioService.obtener(id);
    }


    @PutMapping("/{id}")
    public UsuarioResponseDto actualizar(@PathVariable Long id,
                                         @Valid @RequestBody UsuarioRequestDto dto) {
        return usuarioService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public UsuarioResponseDto actualizarParcial(@PathVariable Long id,
                                                @RequestBody UsuarioRequestDto dto) {
        return usuarioService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        LoginResponseDto response = usuarioService.login(dto);
        HttpStatus status = response.isAutenticado() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(response);
    }
}
