package com.jrwp.appr.dao;

import com.jrwp.appr.entity.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions(Long apprmodelid);
}
