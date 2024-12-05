package org.micro.company.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.micro.company.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования")
class TestingServiceImplTest {

    @InjectMocks
    private TestingServiceImpl testingService;

    @Mock
    private QuestionService questionService;

    @Mock
    private IOService ioService;

    Question question1;
    Question question2;

    @BeforeEach
    void setUp() {
        question1 = new Question("Что такое Java?",
                Arrays.asList("Язык программирования", "Кофе", "Операционная система"),
                1);
        question2 = new Question("Что такое OOP?",
                Arrays.asList("Объектно-ориентированное программирование", "Процедурное программирование"),
                1);
    }

    @Test
    @DisplayName("Все верно")
    void runTests_CorrectAnswers() {
        when(questionService.getQuestions()).thenReturn(List.of(question1));
        when(ioService.readInt()).thenReturn(1);

        int result = testingService.runTests();

        assertEquals(100, result);
        verify(ioService, times(7)).write(anyString());
    }

    @Test
    @DisplayName("Все не верно")
    void runTests_IncorrectAnswers() {
        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        when(ioService.readInt()).thenReturn(2).thenReturn(2);

        int result = testingService.runTests();

        assertEquals(0, result);
        verify(ioService, times(12)).write(anyString());
    }

    @Test
    @DisplayName("Верно половина")
    void runTests_SomeIncorrectAnswers() {
        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        when(ioService.readInt()).thenReturn(2).thenReturn(1);

        int result = testingService.runTests();

        assertEquals(50, result);
        verify(ioService, times(12)).write(anyString());
    }
}
