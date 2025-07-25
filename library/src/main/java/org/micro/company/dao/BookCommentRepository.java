package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookCommentEntity;
import org.micro.company.dto.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookCommentRepository extends CrudRepository<BookCommentEntity, Long> {

    List<BookCommentEntity> findByAuthor(AuthorEntity author);
    List<BookCommentEntity> findByBook(BookEntity book);
}
