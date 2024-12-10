package org.micro.company.service;

import org.micro.company.domain.QuestionData;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    @Value("${question.splitter}")
    public String splitter;
    @Value("${question.filename}")
    private String questionFileName;

    @Override
    public List<QuestionData> getQuestions() {
        List<QuestionData> questionDataList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + questionFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            while ((line = reader.readLine()) != null) {
                QuestionData questionData = new QuestionData();

                String[] data = line.split(splitter);
                int questionIndex = 0;
                int answerIndex = data.length - 1;

                questionData.setQuestion(data[questionIndex]);
                questionData.setRightAnswer(Integer.parseInt(data[answerIndex]));
                questionData.setAnswers(Arrays.stream(Arrays.copyOfRange(data, questionIndex + 1, answerIndex))
                        .toList());

                questionDataList.add(questionData);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return questionDataList;
    }
}
