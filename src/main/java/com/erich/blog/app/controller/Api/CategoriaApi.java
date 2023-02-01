package com.erich.blog.app.controller.Api;

import com.erich.blog.app.dto.CategoriaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.blog.app.utils.paths.Path.APP_ROOT_CA;

public interface CategoriaApi {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = APP_ROOT_CA, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "crea nuevo categoria.", description = "Este método le permite crear  categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto categoria crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto categoria no es válido")
    })
    ResponseEntity<CategoriaDto> save(@Valid @RequestBody CategoriaDto categoriaDto);

    @GetMapping(value = APP_ROOT_CA + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de categorias.", description = "Este método le permite devolver la lista de categorias que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las  categorias"),
    })
    ResponseEntity<List<CategoriaDto>> findAll();

    @GetMapping("{categoriaId}")
    @Operation(summary = "Devuelve la categoria por su id", description = "Este método le permite devolver la categoria por su id que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria por id"),
    })
    ResponseEntity<CategoriaDto> findCategoriaById(@PathVariable Long categoriaId);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = APP_ROOT_CA + "{categoriaId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza la categoria por su id.", description = "Este método le permite actualizar y devolver la nueva categoria que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto categoria crea/modifica por id"),
            @ApiResponse(responseCode = "400", description = "El objeto categoria no es válido")
    })
    ResponseEntity<CategoriaDto> updatCategoriaById(@RequestBody CategoriaDto categoriaDto, @PathVariable Long categoriaId);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(APP_ROOT_CA + "{categoriaId}")
    @Operation(summary = "Eliminamos la categoria", description = "El metodo permite eliminar la categoria de la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminamos la categoria por su id"),
    })
    ResponseEntity<?> deleteCategoriaById(@PathVariable Long categoriaId);
}
