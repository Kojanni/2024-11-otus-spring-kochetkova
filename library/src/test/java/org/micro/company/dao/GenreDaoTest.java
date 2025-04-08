package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dao.impl.GenreDaoImpl;
import org.micro.company.dto.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private GenreDao genreDao;

    GenreEntity genre1;
    GenreEntity genre2;

    @BeforeEach
    void setUp() {
        genreDao = new GenreDaoImpl(jdbcTemplate);

        genre1 = GenreEntity.builder().id(1L).name("Военный роман").build();
        genre2 = GenreEntity.builder().id(2L).name("Комедия").build();
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

        assertNotNull(result);
        assertEquals(newGenre, result.getName());
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