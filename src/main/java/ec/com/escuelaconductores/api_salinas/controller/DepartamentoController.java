package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.DepartamentoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DepartamentoResponseDto;
import ec.com.escuelaconductores.api_salinas.service.DepartamentoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;

@RestController
@RequestMapping(API + "/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoResponseDto> listar(@RequestParam(required = false) String nombre) {
        return departamentoService.listar(nombre);
    }

    @GetMapping("/{id}")
    public DepartamentoResponseDto obtener(@PathVariable Long id) {
        return departamentoService.obtener(id);
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDto> crear(@Valid @RequestBody DepartamentoRequestDto dto) {
        DepartamentoResponseDto creado = departamentoService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public DepartamentoResponseDto actualizar(@PathVariable Long id,
                                              @Valid @RequestBody DepartamentoRequestDto dto) {
        return departamentoService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public DepartamentoResponseDto actualizarParcial(@PathVariable Long id,
                                                     @RequestBody DepartamentoRequestDto dto) {
        return departamentoService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        departamentoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
