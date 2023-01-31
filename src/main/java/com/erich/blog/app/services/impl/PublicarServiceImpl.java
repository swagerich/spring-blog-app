package com.erich.blog.app.services.impl;

import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.entity.Publicar;
import com.erich.blog.app.exception.BadRequestException;
import com.erich.blog.app.exception.PublicacionNotFoundException;
import com.erich.blog.app.repository.PublicarRepo;
import com.erich.blog.app.services.PublicarService;
import com.erich.blog.app.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PublicarServiceImpl implements PublicarService {

    private final PublicarRepo publicarRepo;

    @Override
    public PublicarDto save(PublicarDto publicarDto) {
        Publicar publicar = PublicarDto.toEntity(publicarDto);
        if (publicar != null) {
            Publicar save = publicarRepo.save(publicar);
            return PublicarDto.fromEntity(save);
        }
        return null;
    }


    @Override
    public List<PublicarDto> findAll() {
        return Streamable.of(publicarRepo.findAll())
                .stream().map(PublicarDto::fromEntity).toList();

    }

    @Override
    public PublicarDto findById(Long id) {
        if (id == null) {
            log.error("id es null");
            return null;
        }
        return publicarRepo.findById(id).map(PublicarDto::fromEntity).orElseThrow(() -> new PublicacionNotFoundException("Upps, No se encontro el id : " + id + " en la db"));
    }

    @Override
    public PublicarDto update(PublicarDto publicarDto, Long id) {
        if (!publicarRepo.existsById(id)) {
            throw new PublicacionNotFoundException("No se encontro el id : " + id);
        }
        return publicarRepo.findById(id).map(x -> {
            x.setTitulo(publicarDto.getTitulo());
            x.setDescripcion(publicarDto.getDescripcion());
            x.setContenido(publicarDto.getContenido());
            return PublicarDto.fromEntity(publicarRepo.save(x));
        }).orElseThrow(() -> new BadRequestException("No se pudo actualizar"));
    }


    @Override
    public PublicarDto saveWithPhoto(PublicarDto publicarDto, MultipartFile file) throws IOException {
        Publicar publicar = PublicarDto.toEntity(publicarDto);
        if (publicar != null) {
            if (!file.isEmpty()) {
                publicar.setPhoto(ImageUtil.compressImage(file.getBytes()));
            }
            Publicar save = publicarRepo.save(publicar);
            return PublicarDto.fromEntity(save);
        }
        return null;
    }

    @Override
    public PublicarDto updateWithPhoto(PublicarDto publicarDto, Long id, MultipartFile file) {
        if (!publicarRepo.existsById(id)) {
            throw new PublicacionNotFoundException("No se encontro el id : " + id);
        }
        return publicarRepo.findById(id).map(p -> {
            p.setTitulo(publicarDto.getTitulo());
            p.setDescripcion(publicarDto.getDescripcion());
            p.setContenido(publicarDto.getContenido());
            if (!file.isEmpty()) {
                try {
                    p.setPhoto(ImageUtil.compressImage(file.getBytes()));
                } catch (IOException e) {
                    log.error("Ocurrio un problema en subir la foto");
                    throw new RuntimeException(e.getMessage());
                }
            }
            return PublicarDto.fromEntity(publicarRepo.save(p));
        }).orElseThrow(() -> new BadRequestException("No se pudo actualizar con la foto"));
    }

    @Override
    public Resource viewPhoto(Long id) {
        Publicar publicar = publicarRepo.findById(id).orElseThrow(() -> new PublicacionNotFoundException("No se encontro el id " + id + " con su foto"));
        return new ByteArrayResource(ImageUtil.decompressImage(publicar.getPhoto()));
    }

//    public static byte[] encodeFileToBase64(File file){
//        return Base64.getEncoder().encode();
//    }
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            log.error("id vino null");
            return;
        }
        publicarRepo.deleteById(id);
    }
}
