package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.DistritoRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.DistritoResponseDto;
import ec.com.escuelaconductores.api_salinas.dto.response.EscuelaConductoresResponseDto;
import ec.com.escuelaconductores.api_salinas.service.DistritoService;
import ec.com.escuelaconductores.api_salinas.service.EscuelaConductoresService;
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
@RequestMapping(API + "/districto")
@RequiredArgsConstructor
public class DistritoController {

    private final DistritoService distritoService;
    private final EscuelaConductoresService escuelaConductoresService;

    @GetMapping("/distritos")
    public List<DistritoResponseDto> listar(@RequestParam(required = false) Long idProvincia,
                                            @RequestParam(required = false) String nombre) {
        return distritoService.listar(idProvincia, nombre);
    }

    @GetMapping("/provincias/{idProvincia}/distritos")
    public List<DistritoResponseDto> listarPorProvincia(@PathVariable Long idProvincia,
                                                        @RequestParam(required = false) String nombre) {
        return distritoService.listar(idProvincia, nombre);
    }

    @GetMapping("/distritos/{id}")
    public DistritoResponseDto obtener(@PathVariable Long id) {
        return distritoService.obtener(id);
    }

    @PostMapping("/distritos")
    public ResponseEntity<DistritoResponseDto> crear(@Valid @RequestBody DistritoRequestDto dto) {
        DistritoResponseDto creado = distritoService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/distritos/{id}")
    public DistritoResponseDto actualizar(@PathVariable Long id,
                                          @Valid @RequestBody DistritoRequestDto dto) {
        return distritoService.actualizar(id, dto);
    }

    @PatchMapping("/distritos/{id}")
    public DistritoResponseDto actualizarParcial(@PathVariable Long id,
                                                 @RequestBody DistritoRequestDto dto) {
        return distritoService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/distritos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        distritoService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/distritos/{idDistrito}/escuelas-conductores")
    public List<EscuelaConductoresResponseDto> listarEscuelasPorDistrito(@PathVariable Long idDistrito) {
        return escuelaConductoresService.buscarEstandar(null, null, idDistrito, null, null, null, null);
    }
}
