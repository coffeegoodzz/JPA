package com.example.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class test2 {
	
	private String name;
	private int age;
	
	public static void main(String[] args) {
		test2 test2 = new test2();
		
		test2.setName("확인");
		test2.setAge(2);
		
		System.out.println(test2.getAge());
		System.out.println(test2.getName());
	}
}
