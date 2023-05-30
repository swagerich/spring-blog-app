package com.erich.blog.app.repository;

import com.erich.blog.app.entity.Publicar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicarRepo extends CrudRepository<Publicar,Long> {

    @Query("SELECT p FROM Publicar p INNER JOIN p.comentarios c where c.id=?1")
    List<Publicar> findAllComentarios();

    Page<Publicar> findByCategoriaId(Long categoriaId, Pageable pageable);

    List<Publicar> findByCategoriaId(Long categooriaId);
}
