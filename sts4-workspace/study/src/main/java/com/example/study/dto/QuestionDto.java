package com.example.study.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {
	
	@NotEmpty(message = "제목은 필수항목입니다.") // 검증 실패 시 화면에 표시할 메세지
	@Size(max = 200)
	private String subject;
	
	@NotEmpty(message = "내용은 필수항목입니다.")
	private String content;
}
