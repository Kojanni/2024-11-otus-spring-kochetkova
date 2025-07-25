package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с книгами")
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    private static final int EXPECTED_BOOKS_COUNT = 2;

    BookEntity book1;
    BookEntity book2;
    Long book1Id = 1L;
    Long book2Id = 2L;

    GenreEntity genre1;
    Long genre1Id = 1L;

    AuthorEntity author1;
    AuthorEntity author2;
    Long author1Id = 1L;
    Long author2Id = 2L;

    @BeforeEach
    void setUp() {
        author1 = testEntityManager.find(AuthorEntity.class, author1Id);
        author2 = testEntityManager.find(AuthorEntity.class, author2Id);

        book1 = testEntityManager.find(BookEntity.class, book1Id);
        book2 = testEntityManager.find(BookEntity.class, book2Id);

        genre1 = testEntityManager.find(GenreEntity.class, genre1Id);
    }

    @DisplayName("возращать правильное количество книг")
    @Test
    void testReturnCorrectBookCount() {
        assertThat(bookRepository.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void testInsertBook() {
        BookEntity book = BookEntity.builder().author(author1).genre(genre1).title("Гордый человек").build();

        BookEntity bookSaved = bookRepository.save(book);

        assertNotNull(bookSaved);
        assertThat(bookSaved).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(book);
    }

    @DisplayName("получать нужную книгу по Id")
    @Test
    void testReturnCorrectBookById() {
        Optional<BookEntity> result = bookRepository.findById(book1.getId());

        assertTrue(result.isPresent());
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(book1);
    }

    @DisplayName("получить все книги")
    @Test
    void testReturnCorrectBookList() {
        List<BookEntity> books = Arrays.asList(book1, book2);

        List<BookEntity> actualBooks = bookRepository.findAll();

        assertThat(actualBooks).usingRecursiveComparison()
                .isEqualTo(books);
    }
}