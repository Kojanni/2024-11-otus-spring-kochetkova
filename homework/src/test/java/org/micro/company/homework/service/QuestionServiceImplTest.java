package org.micro.company.homework.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.homework.domain.Question;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения вопросов")
public class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    private final String splitter = ";";
    private final String questionFileName = "questions_test.csv";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(questionService, "splitter", splitter);
        ReflectionTestUtils.setField(questionService, "questionFileName", questionFileName);
    }

    @Test
    @DisplayName("Получен список вопросов")
    public void testGetQuestions() {
        List<Question> questions = questionService.getQuestions();

        assertNotNull(questions);
        assertEquals(1, questions.size());
        Question question = questions.get(0);
        assertEquals("Какой язык программирования является объектно-ориентированным?", question.getQuestion());
        assertEquals(0, question.getRightAnswer());
        assertEquals(List.of("Java", "Python", "C++"), question.getAnswers());
    }

    @Test
    @DisplayName("Ссылка на вопросы не отработала")
    public void testGetQuestions_FileNotFound() throws Exception {
        ReflectionTestUtils.setField(questionService, "questionFileName", "");

        List<Question> questions = questionService.getQuestions();

        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }
}
