package com.itwill.springboot4.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot4.domain.Member;
import com.itwill.springboot4.dto.MemberSignUpDto;
import com.itwill.springboot4.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	
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
		log.info("Post SignUp(dto={})", dto);
		
		// 서비스 계층의 메서드를 호출해서 회원 정보를 테이블에 저장
		Member member = memberService.create(dto);
		log.info("회원가입 성공: {}", member);
		
		return "redirect:/member/signin"; // 회원가입 성공 후 로그인 페이지로 이동(redirect)
	}

}
