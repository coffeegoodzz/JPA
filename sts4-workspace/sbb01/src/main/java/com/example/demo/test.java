package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class test {
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "spring boot sts4 test";
	}
}
