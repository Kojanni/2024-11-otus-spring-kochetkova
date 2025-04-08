package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dao.impl.BookDaoImpl;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private BookDao bookDao;

    private static final int EXPECTED_BOOKS_COUNT = 2;

    BookEntity book1;
    BookEntity book2;

    GenreEntity genre1;
    GenreEntity genre2;

    AuthorEntity author1;
    AuthorEntity author2;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoImpl(jdbcTemplate);

        author1 = AuthorEntity.builder().id(1L).name("Константин").middleName("Михайлович").surname("Симонов").build();
        author2 = AuthorEntity.builder().id(2L).name("Антон").middleName("Павлович").surname("Чехов").build();

        genre1 = GenreEntity.builder().id(1L).name("Военный роман").build();
        genre2 = GenreEntity.builder().id(2L).name("Комедия").build();

        book1 = BookEntity.builder().id(1L).author(author1).genre(genre1).title("Живые и мёртвые").build();
        book2 = BookEntity.builder().id(2L).author(author2).genre(genre2).title("Вишнёвый сад").build();
    }

    @DisplayName("возращать правильное количество книг")
    @Test
    void testReturnCorrectBookCount() {
        assertThat(bookDao.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void testInsertBook() {
        BookEntity book = BookEntity.builder().author(author1).genre(genre1).title("Гордый человек").build();

        BookEntity bookSaved = bookDao.save(book);

        assertNotNull(bookSaved);
        assertThat(bookSaved).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(book);
    }

    @DisplayName("получать нужную книгу по Id")
    @Test
    void testReturnCorrectBookById() {
        BookEntity result = bookDao.findById(book1.getId());

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(book1);
    }

    @DisplayName("получить все книги")
    @Test
    void testReturnCorrectBookList() {
        List<BookEntity> books = Arrays.asList(book1, book2);

        List<BookEntity> actualBooks = bookDao.findAll();

        assertThat(actualBooks).containsAll(books);
    }

}