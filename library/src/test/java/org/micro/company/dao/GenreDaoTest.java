package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dao.impl.GenreDaoImpl;
import org.micro.company.dto.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с жанрами")
@DataJpaTest
@ActiveProfiles("test")
@Import(GenreDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreDao genreDao;

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

        List<GenreEntity> result = genreDao.findAll();

        assertThat(result).containsAll(genres);
    }

    @Test
    void testFindByName() {
        GenreEntity result = genreDao.findByName(genre1.getName());

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(genre1);
    }

    @Test
    void testFindByNameNotFound() {
        String newGenre = "NonExistentGenre";

        GenreEntity result = genreDao.findByName(newGenre);

        assertNull(result);
    }

    @Test
    void testSave() {
        GenreEntity genre = GenreEntity.builder().name("Любовный роман").build();

        GenreEntity result = genreDao.save(genre);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(genre);
    }
}