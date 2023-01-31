package com.erich.blog.app.dto;

import com.erich.blog.app.entity.Publicar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@Builder
public class PublicarDto {

    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String titulo;

    @NotEmpty
    private String descripcion;

    @NotEmpty
    private String contenido;

    @JsonIgnore
    private ComentarioDto comentario;

    public static PublicarDto fromEntity(Publicar publicar) {
        if (publicar == null) {
            log.error("No se pudo convertirs");
            return null;
        }
        return PublicarDto.builder()
                .id(publicar.getId())
                .titulo(publicar.getTitulo())
                .descripcion(publicar.getDescripcion())
                .contenido(publicar.getContenido())
                .build();
    }

    public static Publicar toEntity(PublicarDto publicarDto) {
        if (publicarDto == null) {
            log.error("No se pudo convertirse");
            return null;
        }
        return Publicar.builder()
                .id(publicarDto.getId())
                .titulo(publicarDto.getTitulo())
                .descripcion(publicarDto.getDescripcion())
                .contenido(publicarDto.getContenido())
                .build();
    }

}
