package com.erich.blog.app.controller.Api;

import static com.erich.blog.app.utils.paths.Path.*;

import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.dto.response.PublicationWithPaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PublicarApi {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = APP_ROOT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "crea una nueva publicacion.", description = "Este método le permite crear  publicaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto publicar crea/modifica"),
            @ApiResponse(responseCode = "400", description = "El objeto publicar no es válido")
    })
    ResponseEntity<PublicarDto> save(@Valid @RequestBody PublicarDto publicarDto);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = APP_ROOT + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza la publicacion por su id.", description = "Este método le permite actualizar y devolver la lista de publicaciones que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto publicar crea/modifica por id"),
            @ApiResponse(responseCode = "400", description = "El objeto publicar no es válido")
    })
    ResponseEntity<PublicarDto> update(@Valid @RequestBody PublicarDto publicarDto, @PathVariable Long id);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(APP_ROOT + "/withPhoto")
    @Operation(summary = "Devuelve la vista de la foto.", description = "Este método le permite visualizar la foto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto publicar crea con su foto"),
            @ApiResponse(responseCode = "400", description = "El objeto publicar no es válido")
    })
    ResponseEntity<PublicarDto> saveWithPhoto(@Valid PublicarDto publicarDto, @RequestPart MultipartFile file) throws Exception;


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(APP_ROOT + "/updateWithPhoto/{id}")
    @Operation(summary = "Devuelve la actualizacion de la publicacion y su foto.", description = "Este método le permite actualizar y devolver el objeto  y se  que guardan en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El objeto publicar crea/modifica con su foto por id"),
            @ApiResponse(responseCode = "400", description = "El objeto publicar no es válido")
    })
    ResponseEntity<PublicarDto> updateWithPhoto(@Valid PublicarDto publicarDto, @PathVariable Long id, @RequestPart MultipartFile file) throws IOException;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(APP_ROOT + "/viewPhoto/{id}")
    @Operation(summary = "Devuelve la foto por su id.", description = "Este método le permite  devolver la foto de publicaciones por su id que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El revisamos la foto por su id"),
    })
    ResponseEntity<?> viewPhoto(@PathVariable Long id);

    @GetMapping(value = APP_ROOT + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de publicaciones.", description = "Este método le permite buscar y devolver la lista de publicaciones que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las  publicaciones"),
    })
    ResponseEntity<List<PublicarDto>> findAll();

//    @GetMapping(value = APP_ROOT + "/categoria/{categId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = APP_ROOT + "/categories/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de publicaciones por categoria id.", description = "Este método le permite buscar publicaciones por categorias y devolver la lista de publicaciones que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de paginas de las   publicaciones por categoria"),
    })
    ResponseEntity<PublicationWithPaginatedResponse> getPublicacionesByCategoriaId(@RequestParam(required = false) Long categId , @RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size);

    @GetMapping(value = APP_ROOT + "/{idPublication}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la publicacion por el id.", description = "Este método le permite buscar por el id y devolver la publicacion que existe en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtenemos la publicacion por el id"),
    })
    ResponseEntity<?> findById(@PathVariable Long idPublication);


    @PostMapping(value = APP_ROOT + "/{idPublication}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Incrementamos el like por publicacion.", description = "Este método le permite incrementar likes por la publicacion id  que existe en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incrementacion de likes"),
    })
    ResponseEntity<?> increaseLike(@PathVariable Long idPublication);



    @GetMapping(value = APP_ROOT + "/category/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de publicaciones por categoria id.", description = "Este método le permite buscar publicaciones por categorias y devolver la lista de publicaciones que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las  publicaciones por categoria id"),
    })
    ResponseEntity<?> getAllPublicationsInCategoriaId(@PathVariable Long catId);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(APP_ROOT + "/{id}")
    @Operation(summary = "Eliminamos la publicacion", description = "El metodo permite eliminar la publicacion de la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminamos la publicacion por su id"),
    })
    ResponseEntity<?> delete(@PathVariable Long id);
}
