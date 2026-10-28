package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.PersonaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.PersonaUsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioDatosRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.request.UsuarioRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.PersonaUsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.UsuarioResponseDto;
import ec.com.escuelaconductores.api_salinas.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(API + "/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    public List<PersonaResponseDto> listar() {
        return personaService.listar();
    }

    @GetMapping("/{id}")
    public PersonaResponseDto obtener(@PathVariable Long id) {
        return personaService.obtener(id);
    }

    @PostMapping("/crear-persona-login")
    public ResponseEntity<PersonaUsuarioResponseDto> crear(@RequestBody PersonaUsuarioRequestDto dto) {
        PersonaUsuarioResponseDto creado = personaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getPersona().getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PostMapping("/crear-administrador")
    public ResponseEntity<UsuarioResponseDto> crearPersonaAdmin(@Valid @RequestBody UsuarioDatosRequestDto dto) {
        UsuarioResponseDto creado = personaService.crearPersonaAdmin(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public PersonaResponseDto actualizar(@PathVariable Long id,
                                         @Valid @RequestBody PersonaRequestDto dto) {
        return personaService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public PersonaResponseDto actualizarParcial(@PathVariable Long id,
                                                @RequestBody PersonaRequestDto dto) {
        return personaService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        personaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
