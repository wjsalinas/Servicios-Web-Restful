package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.RolRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.RolResponseDto;
import ec.com.escuelaconductores.api_salinas.service.RolService;
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
@RequestMapping(API + "/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public List<RolResponseDto> listar() {
        return rolService.listar();
    }

    @GetMapping("/{id}")
    public RolResponseDto obtener(@PathVariable Long id) {
        return rolService.obtener(id);
    }

    @PostMapping
    public ResponseEntity<RolResponseDto> crear(@Valid @RequestBody RolRequestDto dto) {
        RolResponseDto creado = rolService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public RolResponseDto actualizar(@PathVariable Long id,
                                     @Valid @RequestBody RolRequestDto dto) {
        return rolService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public RolResponseDto actualizarParcial(@PathVariable Long id,
                                            @RequestBody RolRequestDto dto) {
        return rolService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
