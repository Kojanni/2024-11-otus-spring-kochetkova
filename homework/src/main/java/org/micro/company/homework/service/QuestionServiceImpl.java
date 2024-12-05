package org.micro.company.homework.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.homework.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    @Value("${question.splitter}")
    public String splitter;
    @Value("${question.filename}")
    private String questionFileName;

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + questionFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            while ((line = reader.readLine()) != null) {
                Question question = new Question();

                String[] data = line.split(splitter);
                int questionIndex = 0;
                int answerIndex = data.length - 1;

                question.setQuestion(data[questionIndex]);
                question.setRightAnswer(Integer.parseInt(data[answerIndex]));
                question.setAnswers(Arrays.stream(Arrays.copyOfRange(data, questionIndex + 1, answerIndex))
                        .collect(Collectors.toList()));

                questions.add(question);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }
}
