package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.ProvinciaRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.ProvinciaResponseDto;
import ec.com.escuelaconductores.api_salinas.service.ProvinciaService;
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
@RequestMapping(API + "/provincia")
@RequiredArgsConstructor
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    @GetMapping("/provincias")
    public List<ProvinciaResponseDto> listar(@RequestParam(required = false) Long idDepartamento,
                                             @RequestParam(required = false) String nombre) {
        return provinciaService.listar(idDepartamento, nombre);
    }

    @GetMapping("/departamentos/{idDepartamento}/provincias")
    public List<ProvinciaResponseDto> listarPorDepartamento(@PathVariable Long idDepartamento,
                                                            @RequestParam(required = false) String nombre) {
        return provinciaService.listar(idDepartamento, nombre);
    }

    @GetMapping("/provincias/{id}")
    public ProvinciaResponseDto obtener(@PathVariable Long id) {
        return provinciaService.obtener(id);
    }

    @PostMapping("/provincias")
    public ResponseEntity<ProvinciaResponseDto> crear(@Valid @RequestBody ProvinciaRequestDto dto) {
        ProvinciaResponseDto creada = provinciaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/provincias/{id}")
    public ProvinciaResponseDto actualizar(@PathVariable Long id,
                                           @Valid @RequestBody ProvinciaRequestDto dto) {
        return provinciaService.actualizar(id, dto);
    }

    @PatchMapping("/provincias/{id}")
    public ProvinciaResponseDto actualizarParcial(@PathVariable Long id,
                                                  @RequestBody ProvinciaRequestDto dto) {
        return provinciaService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/provincias/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        provinciaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
