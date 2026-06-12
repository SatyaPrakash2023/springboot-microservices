package com.microservices.Quiz_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.Quiz_Service.Dao.QuizDao;
import com.microservices.Quiz_Service.Feign.QuizInterface;
import com.microservices.Quiz_Service.model.QuestionWrapper;
import com.microservices.Quiz_Service.model.Quiz;
import com.microservices.Quiz_Service.model.Response;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String categoryName, Integer numQuestions, String title) {
        //Here we will call the question microservice to get the question ids based on the category and number of questions required and then save the quiz in our database with those question ids.
        //Call the generate url term as RestTemplate and then use it to call the question microservice and get the question ids.
        List<Integer> questions = quizInterface.generateQuestionForQuiz(categoryName, numQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
       Quiz quiz = quizDao.findById(id).get();
          List<Integer> questionIds = quiz.getQuestionIds();
          ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromId(questionIds);
          return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
