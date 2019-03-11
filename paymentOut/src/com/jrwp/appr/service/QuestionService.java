package com.jrwp.appr.service;

import com.jrwp.appr.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestions(Long apprmodelid);
}
