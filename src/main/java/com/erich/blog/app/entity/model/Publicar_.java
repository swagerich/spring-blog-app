package com.erich.blog.app.entity.model;

import com.erich.blog.app.entity.Categoria;
import com.erich.blog.app.entity.Comentario;
import com.erich.blog.app.entity.Publicar;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Publicar.class)
public abstract class Publicar_ {

    public static volatile SingularAttribute<Publicar, Long> id;

    public static volatile SingularAttribute<Publicar, String> titulo;

    public static volatile SingularAttribute<Publicar, String> descripcion;

    public static volatile SingularAttribute<Publicar, String> contenido;

    public static volatile SetAttribute<Publicar, Comentario> comentarios;

    public static volatile SingularAttribute<Publicar, Categoria> categoria;


    public static final String ID = "id";

    public static final String TITULO = "titulo";

    public static final String DESCRIPTION = "descripcion";

    public static final String CONTENIDO = "contenido";

    public static final String CATEGORIA = "categoria";


}
