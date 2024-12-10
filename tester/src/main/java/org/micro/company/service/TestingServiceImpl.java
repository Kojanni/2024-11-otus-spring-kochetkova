package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.micro.company.domain.QuestionData;
import org.micro.company.domain.TestResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageSource messageSource;

    @Override
    public int runTests() {
        int correctAnswers = 0;
        List<QuestionData> questionDataList = questionService.getQuestions();

        int currentQuestion = 0;
        for (QuestionData questionData : questionDataList) {
            currentQuestion++;
            ioService.write(messageSource.getMessage(
                    "question_number",
                    new Object[]{currentQuestion},
                    LocaleContextHolder.getLocale()));
            ioService.write(questionData.getQuestion());
            for (int i = 1; i <= questionData.getAnswers().size(); i++) {
                ioService.write(i + ". " + questionData.getAnswers().get(i - 1));
            }
            ioService.write(messageSource.getMessage(
                    "answer_number",
                    new Object[]{questionData.getAnswers().size()},
                    LocaleContextHolder.getLocale()));
            int answerIndex = ioService.readInt();
            if (answerIndex == questionData.getRightAnswer()) {
                correctAnswers++;
            }
        }

        int result = Math.round((float) correctAnswers / questionDataList.size() * 100);

        ioService.write(messageSource.getMessage(
                "answer_result",
                new Object[]{result},
                LocaleContextHolder.getLocale()));
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