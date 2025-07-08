package org.micro.company.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.micro.company.dao.GenreDao;
import org.micro.company.dto.GenreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GenreEntity> findAll() {
        return entityManager.createQuery("select g from GenreEntity g", GenreEntity.class)
                .getResultList();
    }

    @Override
    public GenreEntity findByName(String name) {
        TypedQuery<GenreEntity> query = entityManager.createQuery("select b " +
                                "from GenreEntity b " +
                                "where b.name = :name",
                        GenreEntity.class)
                .setParameter("name", name);
        List<GenreEntity> genres = query.getResultList();
        return genres.isEmpty() ? null : genres.get(0);
    }

    @Override
    public GenreEntity save(GenreEntity genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }
}
