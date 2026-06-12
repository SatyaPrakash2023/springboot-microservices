package com.microservices.Question_Service.service;

import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservices.Question_Service.dao.QuestionDao;
import com.microservices.Question_Service.model.Question;
import com.microservices.Question_Service.model.QuestionWrapper;
import com.microservices.Question_Service.model.Response;

@Service
public class QuestionService {


    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        // TODO Auto-generated method stub
        List<Integer> question=questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionsId) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = questionDao.findAllById(questionsId);
        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            questionWrappers.add(wrapper);
        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        // TODO Auto-generated method stub
        int right=0;
        for(Response response: responses){
            Question question=questionDao.findById(response.getId()).orElse(null);
            if(question!=null && question.getRightAnswer().equals(response.getResponse())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
