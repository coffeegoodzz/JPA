package com.example.study.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.study.Entity.Question;
import com.example.study.Repository.QuestionRepository;
import com.example.study.Service.QuestionService;
import com.example.study.dto.AnswerDto;
import com.example.study.dto.QuestionDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id, AnswerDto answerDto) {
		
		Question question = this.questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionDto questionDto) {
		return "question_form";
	}
	
	
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionDto questionDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		this.questionService.create(questionDto.getSubject(), questionDto.getContent());
		return "redirect:/question/list"; // 질문 저장후 질문 목록 이동
	}

}