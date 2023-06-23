package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.dto.response.PublicationWithPaginatedResponse;
import com.erich.blog.app.entity.Categoria;
import com.erich.blog.app.entity.Publicar;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.NotFoundException;
import com.erich.blog.app.repository.CategoriaRepo;
import com.erich.blog.app.repository.PublicarRepo;
import com.erich.blog.app.services.PublicarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicarServiceImpl implements PublicarService {

    private final PublicarRepo publicarRepo;

    private final CategoriaRepo categoriaRepo;

    @Override
    @Transactional
    public PublicarDto save(PublicarDto publicarDto) {

        Categoria categoria = categoriaRepo.findById(publicarDto.getCategoria().getId()).orElseThrow(() -> new NotFoundException("Categoria id no encontrado"));
        Publicar publicar = PublicarDto.toEntity(publicarDto);
        if (publicar != null) {
            publicar.setCategoria(categoria);
            publicar.setLikesCount(0);
            return PublicarDto.fromEntity(publicarRepo.save(publicar));
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PublicarDto> findAll() {
        return Streamable.of(publicarRepo.findAll())
                .stream().map(PublicarDto::fromEntity).toList();

    }

    @Transactional(readOnly = true)
    public List<PublicarDto> findAllAdmin() {
        return Streamable.of(publicarRepo.findAll())
                .stream().map(PublicarDto::fromEntity).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public PublicarDto findById(Long id) {
        if (id == null) {
            log.error("id es null");
            return null;
        }
        return publicarRepo.findById(id).map(PublicarDto::fromEntity).orElseThrow(() -> new NotFoundException("Upps, No se encontro el id : " + id + " en la db"));
    }

    @Transactional(readOnly = true)
    public PublicarDto findByIdAdmin(Long id) {
        if (id == null) {
            log.error("id es null");
            return null;
        }
        return publicarRepo.findById(id).map(PublicarDto::fromEntity).orElseThrow(() -> new NotFoundException("Upps, No se encontro el id : " + id + " en la db"));
    }

    @Override
    @Transactional
    public PublicarDto update(PublicarDto publicarDto, Long id) {
        Categoria categoriaId = categoriaRepo.findById(publicarDto.getCategoria().getId()).orElseThrow(() -> new NotFoundException("Categoria id no encontrado"));
        if (!publicarRepo.existsById(id)) {
            throw new NotFoundException("No se encontro el id : " + id);
        }
        return publicarRepo.findById(id).map(x -> {
            x.setTitulo(publicarDto.getTitulo());
            x.setDescripcion(publicarDto.getDescripcion());
            x.setContenido(publicarDto.getContenido());
            x.setCategoria(categoriaId);
            return PublicarDto.fromEntity(publicarRepo.save(x));
        }).orElseThrow(() -> new BadRequestException("No se pudo actualizar"));
    }


    @Override
    public PublicarDto saveWithPhoto(PublicarDto publicarDto, MultipartFile file) throws IOException {
        //Categoria categoria = categoriaRepo.findById(publicarDto.getCategoria().getId()).orElseThrow(() -> new CategoriaNotFoundException("Categoria id no encontrado"));
        Publicar publicar = PublicarDto.toEntity(publicarDto);
        if (publicar != null) {
            // publicar.setCategoria(categoria);
            if (!file.isEmpty()) {
                publicarDto.setPhoto(file.getBytes());
            }
            Publicar save = publicarRepo.save(publicar);
            return PublicarDto.fromEntity(save);
        }
        return null;
    }

    @Override
    public PublicarDto updateWithPhoto(PublicarDto publicarDto, Long id, MultipartFile file) {
        if (!publicarRepo.existsById(id)) {
            throw new NotFoundException("No se encontro el id : " + id);
        }
        Publicar publicar = PublicarDto.toEntity(publicarDto);
        if (publicar != null) {
            Categoria categoria = categoriaRepo.findById(publicar.getCategoria().getId()).orElseThrow(() -> new NotFoundException("Categoria id no encontrado"));
            return publicarRepo.findById(id).map(p -> {
                p.setTitulo(publicarDto.getTitulo());
                p.setDescripcion(publicarDto.getDescripcion());
                p.setContenido(publicarDto.getContenido());
                p.setCategoria(categoria);
                if (!file.isEmpty()) {
                    try {
                        p.setPhoto(file.getBytes());
                    } catch (IOException e) {
                        log.error("Ocurrio un problema en subir la foto");
                        throw new RuntimeException(e.getMessage());
                    }
                }
                return PublicarDto.fromEntity(publicarRepo.save(p));
            }).orElseThrow(() -> new BadRequestException("No se pudo actualizar con la foto"));
        } else {
            throw new RuntimeException("Ocurrio un problema al actualizar!");
        }
    }

    @Override
    public Resource viewPhoto(Long id) {
        Publicar publicar = publicarRepo.findById(id).orElseThrow(() -> new NotFoundException("No se encontro el id " + id + " con su foto"));
        return new ByteArrayResource(publicar.getPhoto());
    }

    @Override
    @Transactional(readOnly = true)
    public PublicationWithPaginatedResponse getAllPublicacionesByCategoriaId(Long categId, int page, int size) {
        Categoria categoriaId = categoriaRepo.findById(categId).orElseThrow(() -> new NotFoundException("Categoria id " + categId + " no encontrado"));
        Pageable paging = PageRequest.of(page, size);
        Page<Publicar> pageP = publicarRepo.findByCategoriaId(categoriaId.getId(), paging);
        List<PublicarDto> publicarDtos = pageP.getContent().stream().map(PublicarDto::fromEntity).collect(Collectors.toList());
        Map<String, Object> maps = Map.of("pageNumber", pageP.getNumber(), "totalPages", pageP.getTotalPages(), "totalPublications", pageP.getTotalElements());
        return new PublicationWithPaginatedResponse(publicarDtos, maps);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            log.error("id vino null");
            return;
        }
        publicarRepo.deleteComentariosAndPublicacion(id);
        publicarRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void increaseLikesInPublication(Long publiId) {
        Publicar publicar = publicarRepo.findById(publiId).orElseThrow(() -> new NotFoundException("Publication Id no encontrada!"));
        publicar.setLikesCount(publicar.getLikesCount() + 1);
        publicarRepo.save(publicar);
    }

    @Transactional(readOnly = true)
    public Integer getAllLikesInPublicationId(Long publiId) {
        Publicar publicar = publicarRepo.findById(publiId).orElseThrow(() -> new NotFoundException("Publication Id no encontrada!"));
        return publicar.getLikesCount();
    }


    @Override
    @Transactional(readOnly = true)
    public List<PublicarDto> getAllCategoriesByCategorieId(Long categoriId) {
        Categoria cId = categoriaRepo.findById(categoriId).orElseThrow(() -> new NotFoundException(""));
        return Streamable.of(publicarRepo.findByCategoriaId(cId.getId())).stream()
                .map(PublicarDto::fromEntity)
                .toList();
    }
}
