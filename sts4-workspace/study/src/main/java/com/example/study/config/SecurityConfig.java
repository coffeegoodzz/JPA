package com.example.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일 security 설정을 위해 사용
@EnableWebSecurity // 모든 요청 URL이 security의 제어를 받도록 설정
@EnableMethodSecurity(prePostEnabled = true) // 로그인 여부 판별시 @PreAuthorize 의 설정
public class SecurityConfig {
	
	// security를 적용하면 CSRF 기능이 동작한다. -> CSRF 토큰 값을 세션을 통해 발행하고 웹 페이지에서는 폼 전송시에 해당 토큰을 함꼐 전송하여 
	//웹페이지에서 작성된 데이터가 전달 되는지 검증하는 기술
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
			.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) // 모든 인증되지 않은 요청을 허락
			.formLogin((formLogin) -> formLogin
					.loginPage("/user/login")
					.defaultSuccessUrl("/"))
			.logout((logout) -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL 설정
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true));
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		// 객체 생성 방식보다 bean으로 등록하면 사용하면 수정 시에 편리
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		// AuthenticationManager -> 인증을 담당하는데 사용자 인증시 UserSecurityService와 PasswordEncoder를 사용함
		return authenticationConfiguration.getAuthenticationManager();
	}
}
