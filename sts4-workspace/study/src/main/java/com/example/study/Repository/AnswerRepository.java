package com.example.study.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study.Entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
}
