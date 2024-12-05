package org.micro.company.homework.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.micro.company.homework.domain.Person;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис запуска системы тестирования")
public class TestSystemServiceImplTest {

    @InjectMocks
    private TestSystemServiceImpl testSystemService;

    @Mock
    private TestingService testingService;

    @Mock
    private UserRegisterService userRegisterService;


    @Test
    @DisplayName("Пользователь регистрируется и проходит тест на 85")
    public void testStartToTest() {
        Person user =  Person.builder().firstName("Иван").lastName("Иванов").build();

        when(userRegisterService.requestUserByConsole()).thenReturn(user);
        when(userRegisterService.registerUser(user)).thenReturn(user);
        when(testingService.runTests()).thenReturn(85);

        testSystemService.startToTest();

        verify(userRegisterService).requestUserByConsole();
        verify(userRegisterService).registerUser(user);
        verify(testingService).runTests();
        verify(testingService).saveResult(user, 85);
    }
}
