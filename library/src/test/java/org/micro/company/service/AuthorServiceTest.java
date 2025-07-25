package org.micro.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.dao.AuthorRepository;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.service.impl.AuthorServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с авторами библиотеки")
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void testFindAll() {
        AuthorEntity author1 = AuthorEntity.builder().name("John").surname("Smith").build();
        AuthorEntity author2 = AuthorEntity.builder().name("Jane").middleName("Ann").surname("Doe").build();
        List<AuthorEntity> authors = Arrays.asList(author1, author2);

        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorEntity> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Smith", result.get(0).getSurname());
        assertEquals("Doe", result.get(1).getSurname());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindByFullName_WhenAuthorExists() {
        String fullName = "Smith John";
        AuthorEntity expectedAuthor = AuthorEntity.builder().name("John").surname("Smith").build();

        when(authorRepository.findBySurnameAndNameAndMiddleName("Smith", "John", null))
                .thenReturn(Optional.of(expectedAuthor));

        Optional<AuthorEntity> result = authorService.findByFullName(fullName);

        assertTrue(result.isPresent());
        assertEquals(expectedAuthor, result.get());
        verify(authorRepository, times(1)).findBySurnameAndNameAndMiddleName("Smith", "John", null);
    }

    @Test
    void testFindByFullName_WhenAuthorDoesNotExist() {
        String fullName = "Doe Jane";

        when(authorRepository.findBySurnameAndNameAndMiddleName("Doe", "Jane", null))
                .thenReturn(Optional.empty());

        Optional<AuthorEntity> result = authorService.findByFullName(fullName);

        assertFalse(result.isPresent());
        verify(authorRepository, times(1)).findBySurnameAndNameAndMiddleName("Doe", "Jane", null);
    }

    @Test
    void testSave() {
        AuthorEntity newAuthor = AuthorEntity.builder().name("Charlie").middleName("David").surname("Brown").build();

        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(newAuthor);

        AuthorEntity result = authorService.save("Brown", "Charlie", "David");

        assertNotNull(result);
        assertEquals("Brown", result.getSurname());
        assertEquals("Charlie", result.getName());
        assertEquals("David", result.getMiddleName());
        verify(authorRepository, times(1)).save(any(AuthorEntity.class));
    }
}