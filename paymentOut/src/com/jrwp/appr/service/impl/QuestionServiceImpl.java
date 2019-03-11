package com.jrwp.appr.service.impl;

import com.jrwp.appr.dao.QuestionDao;
import com.jrwp.appr.entity.Question;
import com.jrwp.appr.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Resource
    private QuestionDao questionDao;

    @Override
    public List<Question> getQuestions(Long apprmodelid) {
        return questionDao.getQuestions(apprmodelid);
    }
}
