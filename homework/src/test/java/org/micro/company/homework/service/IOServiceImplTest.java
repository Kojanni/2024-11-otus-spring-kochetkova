package org.micro.company.homework.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис ввода/вывода данных")
public class IOServiceImplTest {

    private IOServiceImpl ioService;

    @Mock
    private Scanner scanner;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        ioService = new IOServiceImpl(scanner);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Тестирование метода read для считывания строки")
    public void testRead() {
        when(scanner.nextLine()).thenReturn("Test input");

        String input = ioService.read();

        assertEquals("Test input", input);
    }

    @Test
    @DisplayName("Тестирование метода readInt для считывания целого числа")
    public void testReadInt() {
        when(scanner.nextInt()).thenReturn(42);

        int number = ioService.readInt();

        assertEquals(42, number);
    }

    @Test
    @DisplayName("Тестирование метода write для вывода текста")
    public void testWrite() {
        String text = "Hello, World!";

        ioService.write(text);

        assertEquals("Hello, World!", outputStreamCaptor.toString().trim());
    }
}
