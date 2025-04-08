package org.micro.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.dao.BookDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.impl.BookServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами библиотеки")
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    BookEntity book1;
    BookEntity book2;

    GenreEntity genre1;
    GenreEntity genre2;

    AuthorEntity author1;
    AuthorEntity author2;

    @BeforeEach
    void setUp() {
        author1 = AuthorEntity.builder().name( "John").surname("Smith").build();
        author2 = AuthorEntity.builder().name("Jane").middleName("Ann").surname("Doe").build();

        genre1 = GenreEntity.builder().name("Fiction").build();
        genre2 = GenreEntity.builder().name("Non-Fiction").build();

        book1 = BookEntity.builder().id(1L).author(author1).genre(genre1).title("book1").build();
        book2 = BookEntity.builder().id(2L).author(author2).genre(genre2).title("book2").build();
    }

    @Test
    void testFindAll() {
        List<BookEntity> books = Arrays.asList(book1, book2);

        when(bookDao.findAll()).thenReturn(books);

        List<BookEntity> result = bookService.findAll();

        assertEquals(2, result.size());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
        verify(bookDao, times(1)).findAll();
    }

    @Test
    void testFindById_ValidId() {
        Long id = 1L;

        when(bookDao.findById(id)).thenReturn(book1);

        BookEntity result = bookService.findById(id);

        assertNotNull(result);
        assertEquals(book1, result);
        verify(bookDao, times(1)).findById(id);
    }

    @Test
    void testFindById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookService.findById(null));

        assertEquals("Try to find book by null id", exception.getMessage());
    }

    @Test
    void testFindByAuthor_ValidAuthor() {
        List<BookEntity> books = Arrays.asList(book1);

        when(bookDao.findByAuthor(author1)).thenReturn(books);

        List<BookEntity> result = bookService.findByAuthor(author1);

        assertEquals(1, result.size());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        verify(bookDao, times(1)).findByAuthor(author1);
    }

    @Test
    void testFindByAuthor_NullAuthor() {
        List<BookEntity> result = bookService.findByAuthor(null);

        assertTrue(result.isEmpty());
        verify(bookDao, never()).findByAuthor(any());
    }

    @Test
    void testSaveBook_WhenBookExists() {
        String title = book1.getTitle();

        when(bookDao.findByTitleAndAuthor(title, author1)).thenReturn(book1);

        BookEntity result = bookService.saveBook(title, author1, genre1);

        assertNotNull(result);
        assertEquals(book1, result);
        verify(bookDao, times(1)).findByTitleAndAuthor(title, author1);
        verify(bookDao, never()).save(any());
    }

    @Test
    void testSaveBook_WhenBookDoesNotExist() {
        String title = "New Book";
        BookEntity newBook = BookEntity.builder().title(title).author(author2).genre(genre2).build();
        BookEntity newBookSaved = BookEntity.builder().id(3L).title(title).author(author2).genre(genre2).build();

        when(bookDao.findByTitleAndAuthor(title, author2)).thenThrow(new EmptyResultDataAccessException(1));
        when(bookDao.save(newBook)).thenReturn(newBookSaved);

        BookEntity result = bookService.saveBook(title, author2, genre2);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(author2, result.getAuthor());
        assertEquals(genre2, result.getGenre());
        verify(bookDao, times(1)).findByTitleAndAuthor(title, author2);
        verify(bookDao, times(1)).save(any(BookEntity.class));
    }

    @Test
    void testDeleteById_ValidId() {
        Long id = 1L;

        bookService.deleteById(id);

        verify(bookDao, times(1)).deleteById(id);
    }

    @Test
    void testDeleteById_NullId() {
        bookService.deleteById(null);

        verify(bookDao, never()).deleteById(any());
    }

    @Test
    void testCount() {
        long expectedCount = 5L;
        when(bookDao.count()).thenReturn(expectedCount);

        long result = bookService.count();

        assertEquals(expectedCount, result);
        verify(bookDao, times(1)).count();
    }
}