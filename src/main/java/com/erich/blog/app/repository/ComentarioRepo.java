package com.erich.blog.app.repository;

import com.erich.blog.app.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ComentarioRepo extends JpaRepository<Comentario,Long> {

    List<Comentario> findByPublicarId(Long publiId);

    Comentario findComentarioByIdAndPublicarId(Long comId,Long pubId);
}
