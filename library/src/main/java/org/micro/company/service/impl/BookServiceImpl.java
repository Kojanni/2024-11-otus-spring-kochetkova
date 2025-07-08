package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.BookDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public List<BookEntity> findAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BookEntity findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Try to find book by null id");
        }
        return bookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> findByAuthor(AuthorEntity author) {
        if (author == null) {
            return new ArrayList<>();
        }
        return bookDao.findByAuthor(author);
    }


    @Override
    @Transactional
    public BookEntity saveBook(String title, AuthorEntity author, GenreEntity genre) {
        try {
            return bookDao.findByTitleAndAuthor(title, author);
        } catch (EmptyResultDataAccessException e) {
            return bookDao.save(BookEntity.builder()
                    .title(title)
                    .author(author)
                    .genre(genre)
                    .build());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookDao.count();
    }
}
