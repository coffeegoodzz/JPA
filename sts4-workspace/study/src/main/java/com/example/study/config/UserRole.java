package com.example.study.config;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String value;
	
	UserRole(String value) {
		this.value = value;
	}
	
	// enum = 열거 자료형
}
