package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.entity.Comentario;
import com.erich.blog.app.entity.Publicar;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.ComentarioNotFoundExeption;
import com.erich.blog.app.repository.ComentarioRepo;
import com.erich.blog.app.services.ComentarioService;
import com.erich.blog.app.services.PublicarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepo comentarioRepo;

    private final PublicarService publicarService;

    @Override
    public ComentarioDto save(ComentarioDto comentarioDto, Long id) {
        Comentario comentario = ComentarioDto.toEntity(comentarioDto);
        PublicarDto publicar = publicarService.findById(id);
        Publicar publicarEnt = PublicarDto.toEntity(publicar);
        if (comentario != null) {
            comentario.setPublicar(publicarEnt);
            return ComentarioDto.fromEntity(comentarioRepo.save(comentario));
        }
        return null;
    }

    @Override
    public List<ComentarioDto> findAll() {
        return Streamable.of(comentarioRepo.findAll())
                .stream()
                .map(ComentarioDto::fromEntity)
                .toList();
    }

    @Override
    public ComentarioDto findById(Long id) {
        if (id == null) {
            log.error("el id es null");
            return null;
        }
        return comentarioRepo.findById(id).map(ComentarioDto::fromEntity)
                .orElseThrow(() -> new ComentarioNotFoundExeption("Upps, No se encontro el id"));
    }

    @Override
    public ComentarioDto updateComentarioIdBetweenPublicarId(ComentarioDto comentarioDto, Long comId, Long publId) {
        PublicarDto pId = publicarService.findById(publId);
        Comentario comentario = comentarioRepo.findById(comId).orElseThrow();
        if (!comentario.getPublicar().getId().equals(pId.getId())) {
            throw new BadRequestException("El comentario no coincide con la publicacion");
        }
        return comentarioRepo.findById(comId).map(c -> {
            c.setNombre(comentarioDto.getNombre());
            c.setEmail(comentarioDto.getEmail());
            c.setTexto(comentarioDto.getTexto());
            return ComentarioDto.fromEntity(comentarioRepo.save(c));
        }).orElseThrow(() -> new BadRequestException("Upps !,No se puedo editar !"));

    }

    @Override
    public void deleteByComentarioId(Long comentId, Long publiId) {
        Optional<Comentario> comentario = comentarioRepo.findById(comentId);
        PublicarDto publId = publicarService.findById(publiId);
        if (comentario.isPresent()) {
            Comentario comenId = comentario.get();
            if (!comenId.getPublicar().getId().equals(publId.getId())) {
                throw new BadRequestException("El comentario no coincide con la publicacion");
            }
            comentarioRepo.deleteById(comentId);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ComentarioDto> findByPublicarIdPage(Pageable pageable,Long publiId) {
        Page<Comentario> pages = comentarioRepo.findAll(pageable);
        return new PageImpl<>(comentarioRepo.findByPublicarId(publiId).stream()
                .map(ComentarioDto::fromEntity).filter(Objects::nonNull).toList(),pageable,pages.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioDto findByComentarioIdWithPublicarId(Long comId, Long publId) {
        PublicarDto publiIdDto = publicarService.findById(publId);
        Comentario comentario = comentarioRepo.findById(comId).orElseThrow();
        if (!comentario.getPublicar().getId().equals(publiIdDto.getId())) {
            throw new BadRequestException("El comentario no coincide con la publicacion");
        }
        return ComentarioDto.fromEntity(comentarioRepo.findComentarioByIdAndPublicarId(comentario.getId(), publiIdDto.getId()));
    }


}