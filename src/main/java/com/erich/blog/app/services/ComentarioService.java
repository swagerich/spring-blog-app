package com.erich.blog.app.services;

import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.dto.response.CommentsWithPaginatedResponse;

import java.util.List;
import java.util.Set;

public interface ComentarioService {

    ComentarioDto save(ComentarioDto comentarioDto, Long pId, Long userId);

    List<ComentarioDto> findAll();

    ComentarioDto findById(Long id);

    ComentarioDto updateComentarioIdBetweenPublicarId(ComentarioDto comentarioDto, Long comId, Long publId);

    void deleteComentarioById(Long comentId, Long publiId);

//    List<ComentarioDto> findByPublicarId(Long publiId);

    CommentsWithPaginatedResponse findAllCommentsPaginatedByPublicationId(Long publiId, Integer page, Integer size);

    ComentarioDto findByComentarioIdWithPublicarId(Long comId, Long publId);

    Set<ComentarioDto> findAllComentarioInPublicationId(Long cateId);

}
