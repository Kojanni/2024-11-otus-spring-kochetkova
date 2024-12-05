package org.micro.company.homework.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.homework.domain.Person;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис регистрации пользователей")
public class UserRegisterServiceImplTest {

    @InjectMocks
    private UserRegisterServiceImpl userRegisterService;

    @Mock
    private PersonDataService personDataService;

    @Mock
    private IOService ioService;

    @Mock
    private MessageSource messageSource;

    @Test
    @DisplayName("Пользователь регистрируется без предварительных данных")
    public void testRegisterUser_WithNullPerson() {
        Person person = Person.builder().firstName("Иван").lastName("Иванов").build();
        when(personDataService.savePerson(any(Person.class))).thenReturn(person);

        Person registeredPerson = userRegisterService.registerUser(null);

        assertThat(registeredPerson).isNotNull().usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(person);
        verify(ioService, times(2)).read();
    }
    @Test
    @DisplayName("Пользователь регистрируется с предварительными данными")
    public void testRegisterUser_WithExistingPerson() {
        Person person = Person.builder().firstName("Петр").lastName("Петров").build();
        when(personDataService.savePerson(person)).thenReturn(person);

        Person registeredPerson = userRegisterService.registerUser(person);

        assertThat(registeredPerson).isNotNull().usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(person);
        verify(ioService, never()).read();
    }

    @Test
    @DisplayName("Пользователь вводит данные через консоль")
    public void testRequestUserByConsole() {
        Person person = Person.builder().firstName("Иван").lastName("Иванов").build();
        when(messageSource.getMessage(any(), any(),any())).thenReturn("Введите имя:");
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Введите фамилию:");
        when(ioService.read()).thenReturn("Иван", "Иванов");

        Person result = userRegisterService.requestUserByConsole();

        assertThat(result).isNotNull().usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(person);
        verify(ioService, times(2)).write(anyString());
    }
}