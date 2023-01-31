package com.erich.blog.app.controller;

import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.services.impl.ComentarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comentario")
public class ComentarioController {

    private final ComentarioServiceImpl comentarioService;

    public ComentarioController(ComentarioServiceImpl comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/inPublicacion/{publiId}")
    public ResponseEntity<ComentarioDto> saveComentarioInPublicacion(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long publiId) {
        return new ResponseEntity<>(comentarioService.save(comentarioDto, publiId), HttpStatus.CREATED);
    }

//    @GetMapping("/allcomentInPubli/{publiId}")
//    public ResponseEntity<List<ComentarioDto>> findAllComentarioInPublicacionById(@PathVariable Long publiId) {
//        return new ResponseEntity<>(comentarioService.findByPublicarId(publiId), HttpStatus.OK);
//    }

    @GetMapping("/allcomentInPubli/{publiId}/{page}")
    public ResponseEntity<Page<ComentarioDto>> findAllComentarioInPublicacionById(@PathVariable Integer page, @PathVariable Long publiId) {
        PageRequest pageR = PageRequest.of(page,4);
        return new ResponseEntity<>(comentarioService.findByPublicarIdPage(pageR,publiId), HttpStatus.OK);
    }

    @GetMapping("/{comdId}/{publId}")
    public ResponseEntity<ComentarioDto> findComentIdAndPublicId(@PathVariable Long comdId, @PathVariable Long publId) {
        return new ResponseEntity<>(comentarioService.findByComentarioIdWithPublicarId(comdId, publId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDto>> findAll() {
        return new ResponseEntity<>(comentarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(comentarioService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/comentario-id/{comId}/publicacion-id/{publId}")
    public ResponseEntity<ComentarioDto> updateComentarioIdForPublicarId(@Valid @RequestBody ComentarioDto comentarioDto, @PathVariable Long comId, @PathVariable Long publId) {
        return new ResponseEntity<>(comentarioService.updateComentarioIdBetweenPublicarId(comentarioDto, comId, publId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{comentId}/{publiId}")
    public ResponseEntity<?> deletByIdComentAndPubli(@PathVariable Long comentId, @PathVariable Long publiId) {
        comentarioService.deleteByComentarioId(comentId, publiId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
