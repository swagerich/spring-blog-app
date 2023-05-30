package com.erich.blog.app.controller.Api;

import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.dto.response.CommentsWithPaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.erich.blog.app.utils.paths.Path.*;

import java.util.List;

public interface ComentarioApi {

    @PostMapping(value = APP_ROOT_CO + "/inPublicacion/{publiId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "crea un nuevo comentario en la publicacion.", description = "Este método le permite crear  comentarios en la publicacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto comentario crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto comentario no es válido")
    })
    ResponseEntity<ComentarioDto> saveComentarioInPublicacion(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long publiId, @PathVariable Long userId);

    @GetMapping(value = APP_ROOT_CO + "/allcommentPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de comentarios paginados  por publicaciones id", description = "Este método le permite devolver la lista de comentarios paginados por el id de la publicacion que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de los comentarios con paginacion"),
    })
    ResponseEntity<CommentsWithPaginatedResponse> findAllComentarioInPublicacionById(@RequestParam(required = false) Long publiId , @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size );

    @GetMapping(value = APP_ROOT_CO + "/{comdId}/{publId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve el comentario id junto con la publicacion id.", description = "Este método le permite devolver el  objeto comentario id junto con el objeto publicacion id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtenemos el comentario junto con su publicacion"),
    })
    ResponseEntity<ComentarioDto> findComentIdAndPublicId(@PathVariable Long comdId, @PathVariable Long publId);

    @GetMapping(value = APP_ROOT_CO + "/all")
    @Operation(summary = "Devuelve la lista de comentarios.", description = "Este método le permite devolver la lista de comentarios  que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de los  comentarios"),
    })
    ResponseEntity<List<ComentarioDto>> findAll();

    @GetMapping(value = APP_ROOT_CO + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve el comentario por su id.", description = "Este método le permite devolver el comentarios por id  que existe en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtenemos el comentario mediante el id"),
    })
    ResponseEntity<ComentarioDto> findById(@PathVariable Long id);

    @PutMapping(value = APP_ROOT_CO + "/comentario-id/{comId}/publicacion-id/{publId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza el comentario de la publicacion", description = "Este método le permite actualizar el comentario por la publicacion id y devolver una nuevo comentario que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto comentario crea/modifica por id"),
            @ApiResponse(responseCode = "400", description = "El objeto comentario no es válido")
    })
    ResponseEntity<ComentarioDto> updateComentarioIdForPublicarId(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long comId, @PathVariable Long publId);

    @DeleteMapping(value = APP_ROOT_CO + "/{comentId}/{publiId}")
    @Operation(summary = "Eliminamos el comentario de la publicacion", description = "El metodo permite eliminar el comentario de la publicacion mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminamos el comentario "),
    })
    ResponseEntity<?> deletByIdComentAndPubli(@PathVariable Long comentId, @PathVariable Long publiId);
}
