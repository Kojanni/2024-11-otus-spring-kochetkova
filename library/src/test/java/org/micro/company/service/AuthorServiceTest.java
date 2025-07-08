package org.micro.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.dao.AuthorDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.service.impl.AuthorServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с авторами библиотеки")
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorDao authorDao;

    @Test
    void testFindAll() {
        AuthorEntity author1 = AuthorEntity.builder().name("John").surname("Smith").build();
        AuthorEntity author2 = AuthorEntity.builder().name("Jane").middleName("Ann").surname("Doe").build();
        List<AuthorEntity> authors = Arrays.asList(author1, author2);

        when(authorDao.findAll()).thenReturn(authors);

        List<AuthorEntity> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Smith", result.get(0).getSurname());
        assertEquals("Doe", result.get(1).getSurname());
        verify(authorDao, times(1)).findAll();
    }

    @Test
    void testFindByFullName_WhenAuthorExists() {
        String fullName = "Smith John";
        AuthorEntity expectedAuthor = AuthorEntity.builder().name("John").surname("Smith").build();

        when(authorDao.find("Smith", "John", null)).thenReturn(expectedAuthor);

        AuthorEntity result = authorService.findByFullName(fullName);

        assertNotNull(result);
        assertEquals(expectedAuthor, result);
        verify(authorDao, times(1)).find("Smith", "John", null);
    }

    @Test
    void testFindByFullName_WhenAuthorDoesNotExist() {
        String fullName = "Doe Jane";

        when(authorDao.find("Doe", "Jane", null)).thenReturn(null);
        when(authorDao.save(any(AuthorEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorEntity result = authorService.findByFullName(fullName);

        assertNotNull(result);
        assertEquals("Doe", result.getSurname());
        assertEquals("Jane", result.getName());
        assertNull(result.getMiddleName());
        verify(authorDao, times(1)).find("Doe", "Jane", null);
        verify(authorDao, times(1)).save(any(AuthorEntity.class));
    }

    @Test
    void testSave() {
        AuthorEntity newAuthor = AuthorEntity.builder().name("Charlie").middleName("David").surname("Brown").build();

        when(authorDao.save(any(AuthorEntity.class))).thenReturn(newAuthor);

        AuthorEntity result = authorService.save("Brown", "Charlie", "David");

        assertNotNull(result);
        assertEquals("Brown", result.getSurname());
        assertEquals("Charlie", result.getName());
        assertEquals("David", result.getMiddleName());
        verify(authorDao, times(1)).save(any(AuthorEntity.class));
    }
}