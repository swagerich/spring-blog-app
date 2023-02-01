package com.erich.blog.app.services;

import com.erich.blog.app.dto.CategoriaDto;

import java.util.List;

public interface CategoriaService {

    CategoriaDto save(CategoriaDto categoriaDto);

    List<CategoriaDto> findAll();

    CategoriaDto getCategoriaId(Long id);

    CategoriaDto update(CategoriaDto categoriaDto,Long id);

    void deleteByIdCategoria(Long id);
}
