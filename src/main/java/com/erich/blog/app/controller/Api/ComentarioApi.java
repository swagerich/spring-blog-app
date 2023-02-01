package com.erich.blog.app.controller.Api;

import com.erich.blog.app.dto.ComentarioDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ComentarioApi {


    @PostMapping("/inPublicacion/{publiId}")
    ResponseEntity<ComentarioDto> saveComentarioInPublicacion(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long publiId);

    @GetMapping("/allcomentInPubli/{publiId}/{page}")
    ResponseEntity<Page<ComentarioDto>> findAllComentarioInPublicacionById(@PathVariable Integer page, @PathVariable Long publiId);

    @GetMapping("/{comdId}/{publId}")
    ResponseEntity<ComentarioDto> findComentIdAndPublicId(@PathVariable Long comdId, @PathVariable Long publId);

    @GetMapping
    ResponseEntity<List<ComentarioDto>> findAll();

    @GetMapping("{id}")
    ResponseEntity<ComentarioDto> findById(@PathVariable Long id);

    @PutMapping("/comentario-id/{comId}/publicacion-id/{publId}")
    ResponseEntity<ComentarioDto> updateComentarioIdForPublicarId(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long comId, @PathVariable Long publId);

    @DeleteMapping("/{comentId}/{publiId}")
    ResponseEntity<?> deletByIdComentAndPubli(@PathVariable Long comentId, @PathVariable Long publiId);
}
