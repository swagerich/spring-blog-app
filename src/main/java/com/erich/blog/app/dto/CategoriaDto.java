package com.erich.blog.app.dto;


import com.erich.blog.app.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Data
@Builder
@Slf4j
public class CategoriaDto {

    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String descripcion;

    @JsonIgnore
    private List<PublicarDto> publicars;

    public static CategoriaDto fromEntity(Categoria categoria) {
        if (categoria == null) {
            log.error("Categoria vino null");
            return null;
        }
        return CategoriaDto.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
//                .publicars(categoria.getPublicars() != null ?
//                        categoria.getPublicars().stream().map(PublicarDto::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }

    public static Categoria toEntity(CategoriaDto categoriaDto){
        if(categoriaDto == null){
            log.error("CategoriaDto vino null");
            return null;
        }

        return Categoria.builder()
                .id(categoriaDto.getId())
                .nombre(categoriaDto.getNombre())
                .descripcion(categoriaDto.getDescripcion())
                .build();
    }

}
