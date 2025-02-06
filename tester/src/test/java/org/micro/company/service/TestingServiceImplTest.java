package org.micro.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.domain.Person;
import org.micro.company.domain.QuestionData;
import org.micro.company.domain.TestResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования")
class TestingServiceImplTest {

    @InjectMocks
    private TestingServiceImpl testingService;

    @Mock
    private QuestionService questionService;

    @Mock
    private IOService ioService;

    @Mock
    private MessageSource messageSource;

    QuestionData question1;
    QuestionData question2;

    @BeforeEach
    void setUp() {
        question1 = new QuestionData("Что такое Java?",
                Arrays.asList("Язык программирования", "Кофе", "Операционная система"),
                1);
        question2 = new QuestionData("Что такое OOP?",
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
        verify(ioService, times(4)).write(anyString());
    }

    @Test
    @DisplayName("Все не верно")
    void runTests_IncorrectAnswers() {
        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        when(ioService.readInt()).thenReturn(2).thenReturn(2);

        int result = testingService.runTests();

        assertThat(result).isZero();
        verify(ioService, times(7)).write(anyString());
    }

    @Test
    @DisplayName("Верно половина")
    void runTests_SomeIncorrectAnswers() {
        when(questionService.getQuestions()).thenReturn(List.of(question1, question2));
        when(ioService.readInt()).thenReturn(2).thenReturn(1);

        int result = testingService.runTests();

        assertEquals(50, result);
        verify(ioService, times(7)).write(anyString());
    }

    @Test
    void saveResult() {
        Long usserId = 1L;
        Person user = Person.builder().id(usserId).build();
        int testResult = 75;

        var result = testingService.saveResult(user, testResult);
        TestResult expect = TestResult.builder()
                .id(1L).userId(user.getId()).score(testResult).time(LocalDateTime.now())
                .build();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

}
