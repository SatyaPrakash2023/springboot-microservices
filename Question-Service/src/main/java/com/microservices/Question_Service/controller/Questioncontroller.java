package com.microservices.Question_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.Question_Service.model.Question;
import com.microservices.Question_Service.model.QuestionWrapper;
import com.microservices.Question_Service.model.Response;
import com.microservices.Question_Service.service.QuestionService;


@RestController
@RequestMapping("/questions")
public class Questioncontroller {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    //Generate quiz
    //GetQuestion(QuestionId)
    //GetScore

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> generateQuestionForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions) {
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionsId) {
        // Implementation for fetching a specific question
        return questionService.getQuestionFromId(questionsId);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        // Implementation for calculating the score based on user answers
        return questionService.getScore(responses);
    }

}
