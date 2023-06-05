package com.erich.blog.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publicacion")
@Builder
public class Publicar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 3000)
    private String descripcion;

    private String contenido;

    @Lob
    @Column(length = 500)
    @JsonIgnore
    private byte[] photo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comentario> comentarios = new HashSet<>();

    public Integer getPhotoHashCode() {
        return this.photo != null ? Arrays.hashCode(this.photo) : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id",foreignKey = @ForeignKey(name = "FK_categorias"))
    private Categoria categoria;

    @Column(name = "likes_count")
    private Integer likesCount = 0;

}
