package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.CategoriaDto;
import com.erich.blog.app.entity.Categoria;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.NotFoundException;
import com.erich.blog.app.repository.CategoriaRepo;
import com.erich.blog.app.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepo categoriaRepo;

    @Override
    @Transactional
    public CategoriaDto save(CategoriaDto categoriaDto) {
        Categoria categoria = CategoriaDto.toEntity(categoriaDto);
        if (categoria != null) {
            CategoriaDto.fromEntity(categoriaRepo.save(categoria));
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDto> findAll() {
        return Streamable.of(categoriaRepo.findAll())
                .stream().map(CategoriaDto::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDto getCategoriaId(Long id) {
        Categoria categoria = categoriaRepo.findById(id).orElseThrow(() -> new NotFoundException("No se encontro el id " + id + "en la bd"));
        return CategoriaDto.fromEntity(categoria);
    }

    @Override
    @Transactional
    public CategoriaDto update(CategoriaDto categoriaDto, Long id) {

        if(!categoriaRepo.existsById(id)){
            throw new NotFoundException("No existe el id " + id);
        }

        return categoriaRepo.findById(id).map(c ->{
            c.setNombre(categoriaDto.getNombre());
            c.setDescripcion(categoriaDto.getDescripcion());
            return CategoriaDto.fromEntity(categoriaRepo.save(c));
        }).orElseThrow(() ->  new BadRequestException("No se pudo actualizar la categoria"));
    }

    @Override
    public void deleteByIdCategoria(Long id) {
        if (id == null) {
            log.error("ID vino null");
            return;
        }
        categoriaRepo.deleteById(id);
    }
}
