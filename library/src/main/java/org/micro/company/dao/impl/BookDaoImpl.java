package org.micro.company.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.micro.company.dao.BookDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.micro.company.dao.impl.AuthorDaoImpl.AUTHOR_ID;


@Repository
public class BookDaoImpl implements BookDao {

    public static final String TITLE = "title";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookEntity findById(Long id) {
        List<BookEntity> books = entityManager.createQuery("select b from BookEntity b " +
                                "join fetch b.author " +
                                "join fetch b.genre " +
                                "where b.id = :id",
                        BookEntity.class)
                .setParameter("id", id)
                .getResultList();
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public List<BookEntity> findAll() {
        return entityManager.createQuery("select b from BookEntity b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                        BookEntity.class)
                .getResultList();
    }

    @Override
    public BookEntity findByTitleAndAuthor(String title, AuthorEntity author) {
        TypedQuery<BookEntity> query = entityManager.createQuery("select b " +
                        "from BookEntity b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where b.title = :title " +
                        "and b.author.id = :authorId ",
                BookEntity.class)
                .setParameter(TITLE, title)
                .setParameter(AUTHOR_ID, author.getId());
        return query.getSingleResult();
    }

    @Override
    public List<BookEntity> findByAuthor(AuthorEntity author) {
        TypedQuery<BookEntity> query = entityManager.createQuery("select b " +
                                "from BookEntity b " +
                                "join fetch b.author " +
                                "join fetch b.genre " +
                                "where b.author.id = :authorId ",
                        BookEntity.class)
                .setParameter(AUTHOR_ID, author.getId());
        return query.getResultList();
    }

    @Override
    public BookEntity save(BookEntity book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("delete from BookEntity b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Long count() {
        return entityManager.createQuery("select count(b) from BookEntity b", Long.class).getSingleResult();
    }
}
