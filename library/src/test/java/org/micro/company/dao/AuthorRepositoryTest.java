package org.micro.company.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.dto.AuthorEntity;
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

@DisplayName("Dao для работы с авторами")
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepository authorRepository;

    AuthorEntity author1;
    AuthorEntity author2;
    Long author1Id = 1L;
    Long author2Id = 2L;

    @BeforeEach
    void setUp() {
        author1 = testEntityManager.find(AuthorEntity.class, author1Id);
        author2 = testEntityManager.find(AuthorEntity.class, author2Id);
    }

    @Test
    void testFindBySurnameAndNameAndMiddleNameAll() {
        List<AuthorEntity> authors = Arrays.asList(author1, author2);

        List<AuthorEntity> result = authorRepository.findAll();

        assertThat(result).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(authors);
    }

    @Test
    void testFindBySurnameAndNameAndMiddleName() {
        Optional<AuthorEntity> result = authorRepository.findBySurnameAndNameAndMiddleName(author1.getSurname(), author1.getName(), author1.getMiddleName());

        assertTrue(result.isPresent());
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(author1);
    }

    @Test
    void testFindBySurnameAndNameAndMiddleNameNotFound() {
        Optional<AuthorEntity> result = authorRepository.findBySurnameAndNameAndMiddleName("Doe", "John", "Middle");

        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        AuthorEntity author = AuthorEntity.builder().name("Стивен").middleName("Уильям").surname("Хоккинг").build();

        AuthorEntity result = authorRepository.save(author);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(author);
    }
}