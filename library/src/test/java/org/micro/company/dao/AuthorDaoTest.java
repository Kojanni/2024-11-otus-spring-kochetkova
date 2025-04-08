package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dao.impl.AuthorDaoImpl;
import org.micro.company.dto.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами")
@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthorDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private AuthorDao authorDao;

    AuthorEntity author1;
    AuthorEntity author2;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoImpl(jdbcTemplate);

        author1 = AuthorEntity.builder().id(1L).name("Константин").middleName("Михайлович").surname("Симонов").build();
        author2 = AuthorEntity.builder().id(2L).name("Антон").middleName("Павлович").surname("Чехов").build();
    }

    @Test
    void testFindAll() {
        List<AuthorEntity> authors = Arrays.asList(author1, author2);

        List<AuthorEntity> result = authorDao.findAll();

        assertThat(result).containsAll(authors);
    }

    @Test
    void testFindByFullName() {
        AuthorEntity result = authorDao.findByFullName(author1.getSurname(), author1.getName(), author1.getMiddleName());

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(author1);
    }

    @Test
    void testFindByFullNameNotFound() {
        AuthorEntity result = authorDao.findByFullName("Doe", "John", "Middle");

        assertNull(result);
    }

    @Test
    void testSave() {
        AuthorEntity author = AuthorEntity.builder().name("Стивен").middleName("Уильям").surname("Хоккинг").build();

        AuthorEntity result = authorDao.save(author);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(author);
    }
}