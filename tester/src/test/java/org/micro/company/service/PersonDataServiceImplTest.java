package org.micro.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.micro.company.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Сервис обработки данных")
class PersonDataServiceImplTest {

    private final PersonDataServiceImpl personDataService = new PersonDataServiceImpl();

    @Test
    @DisplayName("Сохранение пользователя")
    void testSavePerson() {
        Person person =  Person.builder().firstName("Иван").lastName("Иванов").build();

        Person result = personDataService.savePerson(person);

        assertThat(result).isNotNull().usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(person);
        assertEquals(1L, result.getId());
    }
}
