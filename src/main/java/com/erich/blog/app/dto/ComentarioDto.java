package com.erich.blog.app.dto;

import com.erich.blog.app.entity.Comentario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Builder
@Slf4j
public class ComentarioDto {

    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;

    @NotEmpty
    private String texto;

    public static ComentarioDto fromEntity(Comentario comentario) {
        if (comentario == null) {
            log.error("Comentario vino null");
            return null;
        }
        return ComentarioDto.builder()
                .id(comentario.getId())
                .nombre(comentario.getNombre())
                .email(comentario.getEmail())
                .texto(comentario.getTexto())
                .build();
    }

    public static Comentario toEntity(ComentarioDto comentarioDto) {
        if (comentarioDto == null) {
            log.error("ComentarioDto vino null");
            return null;
        }
        return Comentario.builder()
                .id(comentarioDto.getId())
                .nombre(comentarioDto.getNombre())
                .email(comentarioDto.getEmail())
                .texto(comentarioDto.getTexto())
                .build();
    }
}
