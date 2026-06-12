package com.microservices.Question_Service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservices.Question_Service.model.Question;

public interface QuestionDao extends JpaRepository<Question, Integer> {

     List<Question> findByCategory(String category);

    @Query(value = """
SELECT q.id
FROM question q
WHERE q.category = :category
ORDER BY RAND()
LIMIT :numQ
""", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}
