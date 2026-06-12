package com.microservices.Quiz_Service.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservices.Quiz_Service.model.QuestionWrapper;
import com.microservices.Quiz_Service.model.Response;

@FeignClient(name="Question-Service")
public interface QuizInterface {

    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> generateQuestionForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions);
        
    @PostMapping("questions/getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionsId);
        

    @PostMapping("questions/getScore")
    public ResponseEntity<Integer> getScore(@RequestParam List<Response> responses);

}
