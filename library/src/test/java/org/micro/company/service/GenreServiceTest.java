package org.micro.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.service.impl.GenreServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.micro.company.dao.GenreRepository;
import org.micro.company.dto.GenreEntity;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с жанрами библиотеки")
@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    GenreEntity genre1;
    GenreEntity genre2;

    @BeforeEach
    void setUp() {
        genre1 = GenreEntity.builder().name("Fiction").build();
        genre2 = GenreEntity.builder().name("Non-Fiction").build();
    }

    @Test
    void testFindAll() {
        List<GenreEntity> genres = Arrays.asList(genre1, genre2);

        when(genreRepository.findAll()).thenReturn(genres);

        List<GenreEntity> result = genreService.findAll();

        assertEquals(2, result.size());
        assertEquals(genre1.getName(), result.get(0).getName());
        assertEquals(genre2.getName(), result.get(1).getName());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void testFindByName_ExistingGenre() {
        String genreName = genre1.getName();

        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre1));

        var result = genreService.findByName(genreName);

        assertTrue(result.isPresent());
        assertEquals(genre1, result.get());
        verify(genreRepository, times(1)).findByName(genreName);
    }

    @Test
    void testFindByName_NonExistingGenre() {
        String genreName = "Fantasy";

        when(genreRepository.findByName(genreName)).thenReturn(Optional.empty());

        var result = genreService.findByName(genreName);

        assertFalse(result.isPresent());
        verify(genreRepository, times(1)).findByName(genreName);
    }

    @Test
    void testSave() {
        String genreName = "Science Fiction";
        GenreEntity savedGenre = GenreEntity.builder().name(genreName).build();

        when(genreRepository.save(any(GenreEntity.class))).thenReturn(savedGenre);

        GenreEntity result = genreService.save(genreName);

        assertNotNull(result);
        assertEquals(genreName, result.getName());
        verify(genreRepository, times(1)).save(any(GenreEntity.class));
    }
}
