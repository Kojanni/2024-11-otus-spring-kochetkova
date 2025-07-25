package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookCommentEntity;
import org.micro.company.dto.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Dao для работы с авторами")
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookCommentRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    BookCommentEntity bookComment1;
    BookCommentEntity bookComment2;
    BookCommentEntity bookComment3;
    Long bookComment1Id = 1L;
    Long bookComment2Id = 2L;
    Long bookComment3Id = 3L;

    BookEntity book1;
    BookEntity book2;
    Long book1Id = 1L;
    Long book2Id = 2L;

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

        bookComment1 = testEntityManager.find(BookCommentEntity.class, bookComment1Id);
        bookComment2 = testEntityManager.find(BookCommentEntity.class, bookComment2Id);
        bookComment3 = testEntityManager.find(BookCommentEntity.class, bookComment3Id);
    }

    @Test
    @DisplayName("получать по Id")
    void testFindById() {
        Optional<BookCommentEntity> comment = bookCommentRepository.findById(bookComment1.getId());

        assertThat(comment).isNotEmpty().usingRecursiveComparison()
                .ignoringFields("value.book.author.books", "value.book.genre")
                .isEqualTo(Optional.of(bookComment1));
    }

    @Test
    void testFindByAuthor() {
        List<BookCommentEntity> comments = bookCommentRepository.findByAuthor(author1);

        assertNotNull(comments);
        assertThat(comments).usingRecursiveComparison()
                .ignoringFields("value.book.author.books", "value.book.genre")
                .isEqualTo(List.of(bookComment1, bookComment3));
    }

    @Test
    void testFindByBook() {
        List<BookCommentEntity> comments = bookCommentRepository.findByBook(book2);

        assertNotNull(comments);
        assertThat(comments).usingRecursiveComparison()
                .ignoringFields("value.book.author.books", "value.book.genre")
                .isEqualTo(List.of(bookComment2, bookComment3));
    }

    @Test
    void testSave() {
        BookCommentEntity comment = BookCommentEntity.builder().author(author2).book(book1).comment("NEW").build();

        BookCommentEntity result = bookCommentRepository.save(comment);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(comment);

        testEntityManager.remove(result);
    }
}