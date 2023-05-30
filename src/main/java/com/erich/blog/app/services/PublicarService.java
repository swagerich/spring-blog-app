package com.erich.blog.app.services;

import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.dto.response.PublicationWithPaginatedResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PublicarService {

    PublicarDto save(PublicarDto publicarDto);

    List<PublicarDto> findAll();

    PublicarDto findById(Long id);

    PublicarDto update(PublicarDto publicarDto, Long id);

    void deleteById(Long id);

    PublicarDto updateWithPhoto(PublicarDto publicarDto, Long id, MultipartFile file);

    PublicarDto saveWithPhoto(PublicarDto publicarDto, MultipartFile multipartFile) throws IOException;

    Resource viewPhoto(Long id);

    PublicationWithPaginatedResponse getAllPublicacionesByCategoriaId(Long categId , int page, int size);

    void increaseLikesInPublication(Long publiId);

    List<PublicarDto> getAllCategoriesByCategorieId(Long categoriId);
}
