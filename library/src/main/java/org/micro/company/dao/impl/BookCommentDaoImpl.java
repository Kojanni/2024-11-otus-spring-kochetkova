package org.micro.company.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.micro.company.dao.BookCommentDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookCommentEntity;
import org.micro.company.dto.BookEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookCommentDaoImpl implements BookCommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookCommentEntity> findById(long id) {
        return Optional.ofNullable(entityManager.find(BookCommentEntity.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCommentEntity> findBookCommentsByAuthor(AuthorEntity author) {
        TypedQuery<BookCommentEntity> query = entityManager.createQuery("select b " +
                        "from BookCommentEntity b " +
                        "where b.author.id = :authorId",
                BookCommentEntity.class);
        query.setParameter("authorId", author.getId());
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCommentEntity> findBookCommentsByBook(BookEntity book) {
        TypedQuery<BookCommentEntity> query = entityManager.createQuery("select b " +
                        "from BookCommentEntity b " +
                        "where b.book.id = :bookId",
                BookCommentEntity.class);
        query.setParameter("bookId", book.getId());
        return query.getResultList();
    }

    @Override
    @Transactional
    public BookCommentEntity save(BookCommentEntity bookComment) {
        if (bookComment.getId() == null) {
            entityManager.persist(bookComment);
            return bookComment;
        } else {
            return entityManager.merge(bookComment);
        }
    }

    @Override
    @Transactional
    public void updateComment(Long id, String comment) {
        BookCommentEntity bookComment = entityManager.find(BookCommentEntity.class, id);
        if (Objects.nonNull(bookComment)) {
            bookComment.setComment(comment);
            entityManager.merge(bookComment);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        entityManager.createQuery("delete from BookCommentEntity b where b.id = :id")
        .setParameter("id", id)
        .executeUpdate();
    }
}
