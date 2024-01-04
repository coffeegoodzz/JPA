package com.example.study.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.study.Entity.Answer;
import com.example.study.Entity.Question;
import com.example.study.Entity.SiteUser;
import com.example.study.Repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public Answer create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		
		return answer;
	}
}
