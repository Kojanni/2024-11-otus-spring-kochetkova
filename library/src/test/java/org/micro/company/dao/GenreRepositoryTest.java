package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с жанрами")
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreRepository genreRepository;

    GenreEntity genre1;
    Long genre1Id = 1L;
    GenreEntity genre2;
    Long genre2Id = 2L;

    @BeforeEach
    void setUp() {
        genre1 = testEntityManager.find(GenreEntity.class, genre1Id);
        genre2 = testEntityManager.find(GenreEntity.class, genre2Id);
    }

    @Test
    void testFindAll() {
        List<GenreEntity> genres = Arrays.asList(genre1, genre2);

        List<GenreEntity> result = genreRepository.findAll();

        assertThat(result).containsAll(genres);
    }

    @Test
    void testFindByName() {
        Optional<GenreEntity> result = genreRepository.findByName(genre1.getName());

        assertTrue(result.isPresent());
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(genre1);
    }

    @Test
    void testFindByNameNotFound() {
        String newGenre = "NonExistentGenre";

        Optional<GenreEntity> result = genreRepository.findByName(newGenre);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSave() {
        GenreEntity genre = GenreEntity.builder().name("Любовный роман").build();

        GenreEntity result = genreRepository.save(genre);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(genre);
    }
}