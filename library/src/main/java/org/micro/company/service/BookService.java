package org.micro.company.service;

import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> findAll();

    List<BookEntity> findByAuthor(AuthorEntity author);

    BookEntity findById(Long id);

    BookEntity saveBook(String title, AuthorEntity author, GenreEntity genre);

    void deleteById(Long id);

    long count();
}
