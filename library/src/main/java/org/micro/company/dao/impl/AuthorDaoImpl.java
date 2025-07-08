package org.micro.company.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.micro.company.dao.AuthorDao;
import org.micro.company.dto.AuthorEntity;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class AuthorDaoImpl implements AuthorDao {

    public static final String AUTHOR_ID = "authorId";
    public static final String SURNAME = "surname";
    public static final String NAME = "name";
    public static final String MIDDLE_NAME = "middleName";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorEntity> findAll() {
        return entityManager.createQuery("select distinct a from AuthorEntity a " +
                        "left join fetch a.books", AuthorEntity.class)
                .getResultList();
    }

    @Override
    public AuthorEntity find(String surname, String name, String middleName) {
        TypedQuery<AuthorEntity> query = entityManager.createQuery("select a from AuthorEntity a " +
                                "where a.name = :name " +
                                "and a.surname = :surname " +
                                "and a.middleName = :middleName",
                        AuthorEntity.class)
                .setParameter(NAME, name)
                .setParameter(SURNAME, Objects.requireNonNullElse(surname, ""))
                .setParameter(MIDDLE_NAME, Objects.requireNonNullElse(middleName, ""));

        List<AuthorEntity> authors = query.getResultList();

        return authors.isEmpty() ? null : authors.get(0);
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }
}
