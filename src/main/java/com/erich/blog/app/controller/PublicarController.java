package com.erich.blog.app.controller;

import com.erich.blog.app.controller.api.PublicarApi;
import com.erich.blog.app.dto.PublicarDto;
import com.erich.blog.app.dto.response.PublicationWithPaginatedResponse;
import com.erich.blog.app.services.impl.PublicarServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PublicarController implements PublicarApi {

    private final PublicarServiceImpl publicarService;

    public PublicarController(PublicarServiceImpl publicarService) {
        this.publicarService = publicarService;
    }

    @Override
    public ResponseEntity<PublicarDto> save(PublicarDto publicarDto) {
        return new ResponseEntity<>(publicarService.save(publicarDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PublicarDto> update(PublicarDto publicarDto, Long id) {
        return new ResponseEntity<>(publicarService.update(publicarDto, id), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PublicarDto> saveWithPhoto(PublicarDto publicarDto, MultipartFile file) throws Exception {
        return new ResponseEntity<>(publicarService.saveWithPhoto(publicarDto, file), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PublicarDto> updateWithPhoto(PublicarDto publicarDto, Long id, MultipartFile file){
        return new ResponseEntity<>(publicarService.updateWithPhoto(publicarDto, id, file), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> viewPhoto(Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(publicarService.viewPhoto(id));
    }

    @Override
    public ResponseEntity<List<PublicarDto>> findAll() {
        return new ResponseEntity<>(publicarService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PublicarDto>> findAllAdmin() {
        return new ResponseEntity<>(publicarService.findAllAdmin(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PublicationWithPaginatedResponse> getPublicacionesByCategoriaId(Long categId, int page, int size) {
        return new ResponseEntity<>(publicarService.getAllPublicacionesByCategoriaId(categId,page,size),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PublicarDto> findById(Long idPublication) {
        return new ResponseEntity<>(publicarService.findById(idPublication),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PublicarDto> findByIdAdmin(Long idPublication) {
        return new ResponseEntity<>(publicarService.findByIdAdmin(idPublication),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PublicarDto> increaseLike(Long idPublication) {
        publicarService.increaseLikesInPublication(idPublication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getLikesInPublicationId(Long idPublication) {
        return new ResponseEntity<>(publicarService.getAllLikesInPublicationId(idPublication),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PublicarDto>> getAllPublicationsInCategoriaId(Long catId) {
        return new ResponseEntity<>(publicarService.getAllCategoriesByCategorieId(catId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        publicarService.deleteById(id);
        return new ResponseEntity<>("Eliminado con exito!!", HttpStatus.NO_CONTENT);
    }
}
