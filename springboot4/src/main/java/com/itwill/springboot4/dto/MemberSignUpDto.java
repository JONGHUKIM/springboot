package com.itwill.springboot4.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.springboot4.domain.Member;

import lombok.Data;

@Data
public class MemberSignUpDto {
	
	private String username;
	private String password;
	private String email;
	
	// DTO 객체를 엔터티(Member) 객체로 변환
	public Member toEntity(PasswordEncoder passwordEncoder) {
		return Member.builder()
					.username(username)
					.password(passwordEncoder.encode(password))
					.email(email)
					.build();
	}
	
}
