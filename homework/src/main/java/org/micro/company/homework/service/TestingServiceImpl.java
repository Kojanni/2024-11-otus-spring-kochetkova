package org.micro.company.homework.service;

import org.micro.company.homework.domain.Question;

import java.util.List;

public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final IOService ioService;

    public TestingServiceImpl(QuestionService questionService, IOService ioService) {
        this.questionService = questionService;
        this.ioService = ioService;
    }

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
            ioService.write("Введите номер ответа: ");
            boolean answerType = false;
            int answerIndex = 0;
            while (!answerType) {
                answerIndex = ioService.readInt();
                if (answerIndex < 1 || answerIndex > question.getAnswers().size()) {
                    answerType = false;
                    ioService.write("Введите цифру от 1 до " + question.getAnswers().size());
                } else answerType = true;
            }
            if (answerIndex == question.getRightAnswer()) {
                correctAnswers++;
            }
        }

        int result = Math.round((float) correctAnswers / questions.size() * 100);

        ioService.write("Тестирование закончено! Ваш результат: " + correctAnswers + "\\" + questions.size());

        return result;
    }
}
