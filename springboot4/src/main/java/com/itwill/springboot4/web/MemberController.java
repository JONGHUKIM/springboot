package com.itwill.springboot4.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot4.dto.MemberSignUpDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@GetMapping("/signin")
	public void signIn() {
		log.info("GET signIn()");
	}
	
	@GetMapping("/signup")
	public void signUp() {
		log.info("GET signUp()");
	}
	
	@PostMapping("/signup")
	public String signUp(MemberSignUpDto dto) {
		
		
		
		return null;
	}

}
