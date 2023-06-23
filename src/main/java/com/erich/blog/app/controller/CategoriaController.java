package com.erich.blog.app.controller;

import com.erich.blog.app.controller.api.CategoriaApi;
import com.erich.blog.app.dto.CategoriaDto;
import com.erich.blog.app.services.impl.CategoriaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaController implements CategoriaApi {

    private final CategoriaServiceImpl categoriaService;

    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }


    @Override
    public ResponseEntity<CategoriaDto> save(CategoriaDto categoriaDto) {
        return new ResponseEntity<>(categoriaService.save(categoriaDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return new ResponseEntity<>(categoriaService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoriaDto> findCategoriaById(Long categoriaId) {
        return new ResponseEntity<>(categoriaService.getCategoriaId(categoriaId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoriaDto> updatCategoriaById(CategoriaDto categoriaDto, Long categoriaId) {
        return new ResponseEntity<>(categoriaService.update(categoriaDto, categoriaId), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteCategoriaById(Long categoriaId) {
        categoriaService.deleteByIdCategoria(categoriaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
