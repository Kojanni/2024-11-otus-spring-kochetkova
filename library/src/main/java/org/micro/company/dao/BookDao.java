package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;

import java.util.List;

public interface BookDao {

    BookEntity findById(Long id);
    List<BookEntity> findAll();
    BookEntity findByTitleAndAuthor(String title, AuthorEntity author);
    List<BookEntity> findByAuthor(AuthorEntity author);
    BookEntity save(BookEntity book);
    void deleteById(Long id);
    Long count();
}
