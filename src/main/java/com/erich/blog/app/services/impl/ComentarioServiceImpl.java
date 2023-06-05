package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.ComentarioDto;
import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.dto.response.CommentsWithPaginatedResponse;
import com.erich.blog.app.entity.Comentario;
import com.erich.blog.app.entity.Publicar;
import com.erich.blog.app.entity.auth.User;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.NotFoundException;
import com.erich.blog.app.repository.ComentarioRepo;
import com.erich.blog.app.repository.PublicarRepo;
import com.erich.blog.app.repository.UserRepo;
import com.erich.blog.app.services.ComentarioService;
import com.erich.blog.app.services.PublicarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class ComentarioServiceImpl implements ComentarioService {
    private final UserRepo userRepo;
    private final PublicarRepo publicarRepo;

    private final ComentarioRepo comentarioRepo;

    private final PublicarService publicarService;

    //USUARIO  HACE UN COMENTARIO A LA PUBLICACION POR EL ID
    @Override
    @Transactional
    public ComentarioDto save(ComentarioDto comentarioDto, Long pId, Long userId) {
        Comentario comentario = ComentarioDto.toEntity(comentarioDto);
        PublicarDto publicar = publicarService.findById(pId);
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        Publicar publicarEnt = PublicarDto.toEntity(publicar);
        if (comentario != null) {
            comentario.setUser(user);
            comentario.setPublicar(publicarEnt);
            return ComentarioDto.fromEntity(comentarioRepo.save(comentario));
        }
        return null;
    }

    //EL USUARIO VERA UNA LISTA DE COMENTARIOS
    @Override
    @Transactional(readOnly = true)
    public List<ComentarioDto> findAll() {
        return Streamable.of(comentarioRepo.findAll())
                .stream()
                .map(ComentarioDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioDto findById(Long id) {
        if (id == null) {
            log.error("el id es null");
            return null;
        }
        return comentarioRepo.findById(id).map(ComentarioDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Upps, No se encontro el id"));
    }

    //EL USUARIO PODRA ACTUALIZAR SU COMENTARIO POR EL ID DE LA PUBLICACION
    @Override
    @Transactional
    public ComentarioDto updateComentarioIdBetweenPublicarId(ComentarioDto comentarioDto, Long comId, Long publId) {
        PublicarDto pId = publicarService.findById(publId);
        Comentario comentario = comentarioRepo.findById(comId).orElseThrow(() -> new NotFoundException("El Comentario por id no fue encontrado!"));
        if (!comentario.getPublicar().getId().equals(pId.getId())) {
            throw new BadRequestException("El comentario no coincide con la publicacion");
        }
        comentario.setNombre(comentarioDto.getNombre());
        comentario.setEmail(comentarioDto.getEmail());
        comentario.setTexto(comentarioDto.getTexto());
        return ComentarioDto.fromEntity(comentarioRepo.save(comentario));
    }

    //ELIMINAMOS EL COMENTARIO
    @Override
    @Transactional
    public void deleteComentarioById(Long comentId, Long publiId) {
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

    // EL USUARIO PODRA VER UNA LISTA PAGINADA DE COMENTARIO POR LA PUBLICACION
    @Override
    @Transactional(readOnly = true)
    public CommentsWithPaginatedResponse findAllCommentsPaginatedByPublicationId(Long publiId, Integer page, Integer size) {
        Publicar publicar = publicarRepo.findById(publiId).orElseThrow(() -> new NotFoundException("Publicacion con " + publiId + " no encontrado"));
        Pageable pageable = PageRequest.of(page,size);
        Page<Comentario> pageComment = comentarioRepo.findByPublicarId(publicar.getId(),pageable);
        Set<ComentarioDto> comentarioDtos = pageComment.getContent().stream().map(ComentarioDto::fromEntity).collect(Collectors.toSet());
        Map<String, Object> mapper = Map.of("totalPages",pageComment.getTotalPages(),"totalComments",pageComment.getTotalElements());
       return new CommentsWithPaginatedResponse(comentarioDtos,mapper);
    }

    //EL USUARIO PODRA VER UN COMENTARIO POR LA PUPLICACION
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


    @Override
    @Transactional(readOnly = true)
    public Set<ComentarioDto> findAllComentarioInPublicationId(Long pubId) {
        Publicar publication = publicarRepo.findById(pubId).orElseThrow(()  ->  new NotFoundException("Publicacion id no encontrada!"));
        Set<Comentario> comment = comentarioRepo.findAllByPublicarId(publication.getId());
        return comment.stream().map(ComentarioDto::fromEntity).collect(Collectors.toSet());
    }
}