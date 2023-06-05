package com.erich.blog.app.repository;

import com.erich.blog.app.entity.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ComentarioRepo extends JpaRepository<Comentario,Long> {

    Set<Comentario> findAllByPublicarId(Long publiId);

    Comentario findComentarioByIdAndPublicarId(Long comId,Long pubId);

    Page<Comentario> findByPublicarId(Long publiId, Pageable pageable);
}
