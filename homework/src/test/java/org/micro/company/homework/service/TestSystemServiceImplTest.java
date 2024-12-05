package org.micro.company.homework.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.micro.company.homework.domain.Person;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestSystemServiceImplTest {

    @InjectMocks
    private TestSystemServiceImpl testSystemService;

    @Mock
    private TestingService testingService;

    @Mock
    private UserRegisterService userRegisterService;

    @Mock
    private IOService ioService; // Если используется в вашем классе

    @Test
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
