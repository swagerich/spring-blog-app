package com.erich.blog.app.repository;

import com.erich.blog.app.entity.Publicar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicarRepo extends CrudRepository<Publicar,Long> {

    @Query("SELECT p FROM Publicar p INNER JOIN p.comentarios c where c.id=?1")
    List<Publicar> findAllComentarios();

    List<Publicar> findByCategoriaId(Long categoriaId);
}
