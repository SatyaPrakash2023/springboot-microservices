package com.microservices.Quiz_Service.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.Quiz_Service.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
