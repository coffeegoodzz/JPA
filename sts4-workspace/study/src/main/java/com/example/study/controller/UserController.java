package com.example.study.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.study.Service.UserService;
import com.example.study.dto.UserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserDto userDto) {
		return "signup";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "signup";
		}
		
		if(!userDto.getPassword1().equals(userDto.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다."); // 필드명,오류코드,에러메세지
			return "signup";
		}
		
		try {
			
			userService.crete(userDto.getUsername(), userDto.getEmail(), userDto.getPassword1());
			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace(); // 아이디 이메일 중복시 발생오류 처리
			
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup";
		}catch(Exception e) {
			e.printStackTrace(); // 그 외 오류 처리
			bindingResult.reject("signupFailed", e.getMessage());
			
			return "signup";
		}
		return "redirect:/";
	}
}
