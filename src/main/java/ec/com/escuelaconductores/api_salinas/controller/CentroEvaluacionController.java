package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.CentroEvaluacionRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.CentroEvaluacionResponseDto;
import ec.com.escuelaconductores.api_salinas.service.CentroEvaluacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;

@RestController
@RequestMapping(API + "/centros-evaluacion")
@RequiredArgsConstructor
public class CentroEvaluacionController {

    private final CentroEvaluacionService centroEvaluacionService;

    @GetMapping
    public List<CentroEvaluacionResponseDto> buscarEstandar(
            @RequestParam(required = false) Long idDepartamento,
            @RequestParam(required = false) Long idProvincia,
            @RequestParam(required = false) Long idDistrito,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) String nombre) {
        return centroEvaluacionService.buscarEstandar(
                idDepartamento, idProvincia, idDistrito, departamento, provincia, distrito, nombre);
    }

    @GetMapping("/all")
    public List<CentroEvaluacionResponseDto> listar() {
        return centroEvaluacionService.listar();
    }

    @PostMapping
    public ResponseEntity<CentroEvaluacionResponseDto> crear(@Valid @RequestBody CentroEvaluacionRequestDto dto) {
        CentroEvaluacionResponseDto creado = centroEvaluacionService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public CentroEvaluacionResponseDto actualizar(@PathVariable Long id,
                                                  @Valid @RequestBody CentroEvaluacionRequestDto dto) {
        return centroEvaluacionService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public CentroEvaluacionResponseDto actualizarParcial(@PathVariable Long id,
                                                         @RequestBody CentroEvaluacionRequestDto dto) {
        return centroEvaluacionService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        centroEvaluacionService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}