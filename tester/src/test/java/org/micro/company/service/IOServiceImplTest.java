package org.micro.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис ввода/вывода данных")
class IOServiceImplTest {

    private IOServiceImpl ioService;

    @Mock
    private Scanner scanner;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
     void setUp() {
        ioService = new IOServiceImpl(scanner);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Тестирование метода read для считывания строки")
     void testRead() {
        when(scanner.nextLine()).thenReturn("Test input");

        String input = ioService.read();

        assertEquals("Test input", input);
    }

    @Test
    @DisplayName("Тестирование метода readInt для считывания целого числа")
     void testReadInt() {
        when(scanner.nextInt()).thenReturn(42);

        int number = ioService.readInt();

        assertEquals(42, number);
    }

    @Test
    @DisplayName("Тестирование метода write для вывода текста")
     void testWrite() {
        String text = "Hello, World!";

        ioService.write(text);

        assertEquals("Hello, World!", outputStreamCaptor.toString().trim());
    }
}