package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroMedicoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroMedicoResponseDto;
import ec.com.escuelaconductores.api_salinas.service.CentroMedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;

@RestController
@RequestMapping(API + "/centros-medicos")
@RequiredArgsConstructor
public class CentroMedicoController {

    private final CentroMedicoService centroMedicoService;

    @GetMapping
    public List<CentroMedicoResponseDto> buscarEstandar(
            @RequestParam(required = false) Long idDepartamento,
            @RequestParam(required = false) Long idProvincia,
            @RequestParam(required = false) Long idDistrito,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) String nombre) {
        return centroMedicoService.buscarEstandar(
                idDepartamento, idProvincia, idDistrito, departamento, provincia, distrito, nombre);
    }

    @GetMapping("/all")
    public List<CentroMedicoResponseDto> listar() {
        return centroMedicoService.listar();
    }

    @PostMapping
    public ResponseEntity<CentroMedicoResponseDto> crear(@Valid @RequestBody CentroMedicoRequestDto dto) {
        CentroMedicoResponseDto creado = centroMedicoService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public CentroMedicoResponseDto actualizar(@PathVariable Long id,
                                              @Valid @RequestBody CentroMedicoRequestDto dto) {
        return centroMedicoService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public CentroMedicoResponseDto actualizarParcial(@PathVariable Long id,
                                                     @RequestBody CentroMedicoRequestDto dto) {
        return centroMedicoService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        centroMedicoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}