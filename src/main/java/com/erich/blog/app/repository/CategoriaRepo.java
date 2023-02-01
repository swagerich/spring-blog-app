package com.erich.blog.app.repository;

import com.erich.blog.app.entity.Categoria;
import org.springframework.data.repository.CrudRepository;



public interface CategoriaRepo extends CrudRepository<Categoria, Long> {

}