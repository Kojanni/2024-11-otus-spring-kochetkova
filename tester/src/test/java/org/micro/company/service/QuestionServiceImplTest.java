package org.micro.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.micro.company.domain.QuestionData;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения вопросов")
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    private final String splitter = ";";
    private final String baseFileName = "questions";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(questionService, "splitter", splitter);
        ReflectionTestUtils.setField(questionService, "baseFileName", baseFileName);
    }

    @Test
    @DisplayName("Получен список вопросов")
    void testGetQuestions() {
        List<QuestionData> questions = questionService.getQuestions();

        assertNotNull(questions);
        assertEquals(1, questions.size());
        QuestionData question = questions.get(0);
        assertEquals("Какой язык программирования является объектно-ориентированным?", question.getQuestion());
        assertEquals(0, question.getRightAnswer());
        assertEquals(List.of("Java", "Python", "C++"), question.getAnswers());
    }

    @Test
    @DisplayName("Ссылка на вопросы не отработала")
    void testGetQuestions_FileNotFound() {
        ReflectionTestUtils.setField(questionService, "baseFileName", "");

        List<QuestionData> questions = questionService.getQuestions();

        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }
}
