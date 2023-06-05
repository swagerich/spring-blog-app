package com.erich.blog.app.entity.model;

import com.erich.blog.app.entity.Comentario;
import com.erich.blog.app.entity.Publicar;
import com.erich.blog.app.entity.auth.User;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comentario.class)
public abstract class Comentario_ {

    public static volatile SingularAttribute<Comentario, Long> id;

    public static volatile SingularAttribute<Comentario, String> nombre;

    public static volatile SingularAttribute<Comentario, String> email;

    public static volatile SingularAttribute<Comentario, String> texto;

    public static volatile SingularAttribute<Comentario, Publicar> publicar;

    public static volatile SingularAttribute<Comentario, User> user;


    public static final String ID = "id";

    public static final String NOMBRE = "nombre";

    public static final String EMAIL = "email";

    public static final String TEXTO = "texto";

    public static final String PUBLICAR = "publicar";


}
