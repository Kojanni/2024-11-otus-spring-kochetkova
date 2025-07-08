package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookCommentEntity;
import org.micro.company.dto.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookCommentDao {

    Optional<BookCommentEntity> findById(long id);

    List<BookCommentEntity> findBookCommentsByAuthor(AuthorEntity author);
    List<BookCommentEntity> findBookCommentsByBook(BookEntity book);
    BookCommentEntity save(BookCommentEntity bookComment);
    void updateComment(Long id, String comment);
    void deleteComment(Long id);
}
