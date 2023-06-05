package com.erich.blog.app.repository.custom.impl;

import com.erich.blog.app.entity.Comentario;
import com.erich.blog.app.entity.model.Comentario_;
import com.erich.blog.app.repository.custom.PublicationRepoCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

public class PublicationRepoCustomImpl implements PublicationRepoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteComentariosAndPublicacion(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaDelete<Comentario> deleteComent = criteriaBuilder.createCriteriaDelete(Comentario.class);
        Root<Comentario> comentarioRoot = deleteComent.from(Comentario.class);
        deleteComent.where(criteriaBuilder.equal(comentarioRoot.get(Comentario_.PUBLICAR).get(Comentario_.ID),id));

        //CriteriaDelete<Publicar> deletePublicacion = criteriaBuilder.createCriteriaDelete(Publicar.class);
       // Root<Publicar> publicacionRoot = deletePublicacion.from(Publicar.class);
       // deletePublicacion.where(criteriaBuilder.equal(publicacionRoot.get(Publicar_.ID), id));

        em.createQuery(deleteComent).executeUpdate();
        //em.createQuery(deletePublicacion).executeUpdate();
    }
}
