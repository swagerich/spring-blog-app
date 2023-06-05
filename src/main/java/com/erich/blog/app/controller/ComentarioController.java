package com.erich.blog.app.controller;

import com.erich.blog.app.controller.Api.ComentarioApi;
import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.dto.response.CommentsWithPaginatedResponse;
import com.erich.blog.app.services.impl.ComentarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ComentarioController implements ComentarioApi {

    private final ComentarioServiceImpl comentarioService;

    public ComentarioController(ComentarioServiceImpl comentarioService) {
        this.comentarioService = comentarioService;
    }

    @Override
    public ResponseEntity<ComentarioDto> saveComentarioInPublicacion(ComentarioDto comentarioDto, Long publiId, Long userId) {
        return new ResponseEntity<>(comentarioService.save(comentarioDto, publiId,userId), HttpStatus.CREATED);
    }

    //    @GetMapping("/allcomentInPubli/{publiId}")
//    public ResponseEntity<List<ComentarioDto>> findAllComentarioInPublicacionById(@PathVariable Long publiId) {
//        return new ResponseEntity<>(comentarioService.findByPublicarId(publiId), HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<CommentsWithPaginatedResponse> findAllComentarioInPublicacionById(Long publiId , Integer page, Integer size ) {
        return new ResponseEntity<>(comentarioService.findAllCommentsPaginatedByPublicationId(publiId,page,size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ComentarioDto> findComentIdAndPublicId(Long comdId, Long publId) {
        return new ResponseEntity<>(comentarioService.findByComentarioIdWithPublicarId(comdId, publId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ComentarioDto>> findAll() {
        return new ResponseEntity<>(comentarioService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ComentarioDto> findById(Long id) {
        return new ResponseEntity<>(comentarioService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ComentarioDto> updateComentarioIdForPublicarId(ComentarioDto comentarioDto, Long comId, Long publId) {
        return new ResponseEntity<>(comentarioService.updateComentarioIdBetweenPublicarId(comentarioDto, comId, publId), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deletByIdComentAndPubli(Long comentId, Long publiId) {
        comentarioService.deleteComentarioById(comentId, publiId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Set<ComentarioDto>> findAllComentarioInPublicacionById(Long publiId) {
        return new ResponseEntity<>(comentarioService.findAllComentarioInPublicationId(publiId),HttpStatus.OK);
    }
}
