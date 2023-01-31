package com.erich.blog.app.services;

import com.erich.blog.app.dto.ComentarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComentarioService {

    ComentarioDto save(ComentarioDto comentarioDto, Long id);

    List<ComentarioDto> findAll();

    ComentarioDto findById(Long id);

    ComentarioDto updateComentarioIdBetweenPublicarId(ComentarioDto comentarioDto, Long comId, Long publId);

    void deleteByComentarioId(Long comentId, Long publiId);

//    List<ComentarioDto> findByPublicarId(Long publiId);

    Page<ComentarioDto> findByPublicarIdPage(Pageable pageable, Long publiId);

    ComentarioDto findByComentarioIdWithPublicarId(Long comId, Long publId);


}
