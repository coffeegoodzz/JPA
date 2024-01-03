package com.example.study.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.study.Entity.SiteUser;
import com.example.study.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser crete(String username, String email, String password) {
		SiteUser siteUser = new SiteUser();
		
		siteUser.setUsername(username);
		siteUser.setEmail(email);
		siteUser.setPassword(passwordEncoder.encode(password));
		
		this.userRepository.save(siteUser);
		
		return siteUser;
	}
}
