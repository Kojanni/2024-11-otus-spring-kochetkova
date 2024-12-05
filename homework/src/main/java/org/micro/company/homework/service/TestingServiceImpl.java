package org.micro.company.homework.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.homework.domain.Person;
import org.micro.company.homework.domain.Question;
import org.micro.company.homework.domain.TestResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final IOService ioService;

    @Override
    public int runTests() {
        int correctAnswers = 0;
        List<Question> questions = questionService.getQuestions();

        int currentQuestion = 0;
        for (Question question : questions) {
            currentQuestion++;
            ioService.write("Вопрос №" + currentQuestion + ": ");
            ioService.write(question.getQuestion());
            for (int i = 1; i <= question.getAnswers().size(); i++) {
                ioService.write(i + ". " + question.getAnswers().get(i - 1));
            }
            ioService.write("Введите номер ответа (цифру от 1 до " + question.getAnswers().size() + ")");
            int answerIndex = ioService.readInt();
            if (answerIndex == question.getRightAnswer()) {
                correctAnswers++;
            }
        }

        int result = Math.round((float) correctAnswers / questions.size() * 100);

        ioService.write("Тестирование закончено! Ваш результат: " + correctAnswers + "\\" + questions.size());

        return result;
    }

    @Override
    public TestResult saveResult(Person user, int testResult) {
        //тут типо  в БД сохранили
        return TestResult.builder()
                .id(1L)
                .userId(user.getId())
                .score(testResult)
                .time(LocalDateTime.now())
                .build();
    }
}
