package ec.com.escuelaconductores.api_salinas.controller;

import ec.com.escuelaconductores.api_salinas.dto.request.EscuelaConductoresRequestDto;
import ec.com.escuelaconductores.api_salinas.dto.response.EscuelaConductoresResponseDto;
import ec.com.escuelaconductores.api_salinas.service.EscuelaConductoresService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(API + "/escuelas-conductores")
@RequiredArgsConstructor
public class EscuelaConductoresController {

    private final EscuelaConductoresService escuelaConductoresService;

    @GetMapping
    public List<EscuelaConductoresResponseDto> listar(
            @RequestParam(required = false, defaultValue = "estandar") String option,
            @RequestParam(required = false) Long idDepartamento,
            @RequestParam(required = false) Long idProvincia,
            @RequestParam(required = false) Long idDistrito,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String q) {
        List<EscuelaConductoresResponseDto> resultado = "rapida".equalsIgnoreCase(option)
                ? escuelaConductoresService.buscarRapida(q)
                : escuelaConductoresService.buscarEstandar(
                idDepartamento, idProvincia, idDistrito, departamento, provincia, distrito, nombre);
        return resultado;
    }

    @GetMapping("/navegar")
    public CollectionModel<EntityModel<EscuelaConductoresResponseDto>> listarEscuelas(
            @RequestParam(required = false, defaultValue = "estandar") String option,
            @RequestParam(required = false) Long idDepartamento,
            @RequestParam(required = false) Long idProvincia,
            @RequestParam(required = false) Long idDistrito,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String q) {
        List<EscuelaConductoresResponseDto> resultado = "rapida".equalsIgnoreCase(option)
                ? escuelaConductoresService.buscarRapida(q)
                : escuelaConductoresService.buscarEstandar(
                idDepartamento, idProvincia, idDistrito, departamento, provincia, distrito, nombre);
        return findAllModelDto(resultado);
    }

    @GetMapping("/pagin-bd")
    public Page<EscuelaConductoresResponseDto> listarPaginBd(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            @RequestParam(value = "nombre", defaultValue = "") String nombre) {
        Pageable pageable = PageRequest.of(page-1, tamanio(size));
        return escuelaConductoresService.listarPaginBd(pageable, nombre);
    }

    @GetMapping("/pagin-orden-bd")
    public Page<EscuelaConductoresResponseDto> listarPaginOrdenBd(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            @RequestParam(value = "nombre", defaultValue = "") String nombre,
            @RequestParam(value = "field", defaultValue = "nombre") String field,
            @RequestParam(value = "order", defaultValue = "ASC") String order,
            @RequestParam(value = "field2", defaultValue = "id") String field2,
            @RequestParam(value = "order2", defaultValue = "ASC") String order2) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), field)
                .and(Sort.by(Sort.Direction.fromString(order2), field2));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return escuelaConductoresService.listarPaginBd(pageable, nombre);
    }

    @GetMapping("/pagin-memoria")
    public Page<EscuelaConductoresResponseDto> listarPaginMemoria(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            @RequestParam(value = "nombre", defaultValue = "") String nombre) {
        return escuelaConductoresService.listarPaginMemoria(page, size, nombre);
    }

    @GetMapping("/pagin-orden-memoria")
    public Page<EscuelaConductoresResponseDto> listarPaginOrdenMemoria(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            @RequestParam(value = "nombre", defaultValue = "") String nombre,
            @RequestParam(value = "field", defaultValue = "nombre") String field,
            @RequestParam(value = "order", defaultValue = "ASC") String order,
            @RequestParam(value = "field2", defaultValue = "id") String field2,
            @RequestParam(value = "order2", defaultValue = "ASC") String order2) {
        return escuelaConductoresService.listarPaginOrdenMemoria(page, size, nombre, field, order, field2, order2);
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<EscuelaConductoresResponseDto>> getAllEscuelas() {
        List<EscuelaConductoresResponseDto> resultado = escuelaConductoresService.buscarEstandar(
                null, null, null, null, null, null, null);
        return findAllModelDto(resultado);
    }

    @GetMapping("/{id}")
    public EscuelaConductoresResponseDto obtener(@PathVariable Long id) {
        return escuelaConductoresService.obtener(id);
    }

    @PostMapping
    public ResponseEntity<EscuelaConductoresResponseDto> crear(
            @Valid @RequestBody EscuelaConductoresRequestDto dto) {
        EscuelaConductoresResponseDto creada = escuelaConductoresService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public EscuelaConductoresResponseDto actualizar(@PathVariable Long id,
            @Valid @RequestBody EscuelaConductoresRequestDto dto) {
        return escuelaConductoresService.actualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public EscuelaConductoresResponseDto actualizarParcial(@PathVariable Long id,
            @RequestBody EscuelaConductoresRequestDto dto) {
        return escuelaConductoresService.actualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        escuelaConductoresService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activar")
    public EscuelaConductoresResponseDto activar(@PathVariable Long id) {
        return escuelaConductoresService.activar(id);
    }

    private CollectionModel<EntityModel<EscuelaConductoresResponseDto>> findAllModelDto(
            List<EscuelaConductoresResponseDto> lista) {
        List<EntityModel<EscuelaConductoresResponseDto>> items = lista.stream().map(this::findByIdModelDto).toList();
        CollectionModel<EntityModel<EscuelaConductoresResponseDto>> collection = CollectionModel.of(items);
        collection.add(linkTo(methodOn(EscuelaConductoresController.class).getAllEscuelas()).withSelfRel());
        return collection;
    }

    private EntityModel<EscuelaConductoresResponseDto> findByIdModelDto(EscuelaConductoresResponseDto dto) {
        EntityModel<EscuelaConductoresResponseDto> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(EscuelaConductoresController.class).obtener(dto.getId())).withSelfRel());
        model.add(linkTo(methodOn(DepartamentoController.class).obtener(dto.getIdDepartamento())).withRel("departamento"));
        model.add(linkTo(methodOn(ProvinciaController.class).obtener(dto.getIdProvincia())).withRel("provincia"));
        model.add(linkTo(methodOn(DistritoController.class).obtener(dto.getIdDistrito())).withRel("distrito"));
        return model;
    }

    private int tamanio(Integer size) {
        return size < 1 ? 10 : Math.min(size, 100);
    }
}
