package org.micro.company.service;

import org.micro.company.domain.QuestionData;

import java.util.List;

public interface QuestionService {
    List<QuestionData> getQuestions();
}
