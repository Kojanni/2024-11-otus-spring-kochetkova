package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.BookRepository;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookEntity> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Try to find book by null id");
        }
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> findByAuthor(AuthorEntity author) {
        if (author == null) {
            return new ArrayList<>();
        }
        return bookRepository.findByAuthor(author);
    }


    @Override
    @Transactional
    public BookEntity saveBook(String title, AuthorEntity author, GenreEntity genre) {
        Optional<BookEntity> book = bookRepository.findByTitleAndAuthor(title, author);
        return book.orElseGet(() -> bookRepository.save(BookEntity.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }
}
