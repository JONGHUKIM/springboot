package com.itwill.springboot4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// -> 스프링 컨테이너에서 생성하고 관리하는 설정 컴포넌트
// -> 스프링 컨테이너에서 필요한 곳에 의존성 주입해 줄 수 있음 
public class SecurityConfig {

	// Spring Security 5 버전부터 비밀번호는 반드시 암호화(encoding)를 해야만 함
	// 만약 비밀번호를 암호화하지 않으면 HTTP 403(access denied, 접근 거부) 또는
	// HTTP 500 (internal server error, 내부 서버 오류) 에러가 발생
	// 비밀번호를 암호화하는 객체를 스프링 컨테이너가 빈으로 관리해야 함
	@Bean // -> 스프링 컨테이너에서 관리하는 객체(빈) -> 의존성 주입
	PasswordEncoder passwordEncoder() {
		log.info("BCryptPasswordEncoder 생성");
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * UserDetailsService:
	 * 사용자 관리(로그인, 로그아웃, 회원가입 등)를 위한 서비스 인터페이스
	 * 스프링 부트 애플리케이션에서 스프링 시큐리티를 이용한 로그인/로그아웃 서비스를 구현하려면
	 * (1) UserDetailsService 인터페이스를 구현하는 서비스 클래스와 
	 * (2) UserDetails 인터페이스를 구현하는 엔터티 클래스가 있어야 함
	 *  
	 * 사용자(user) 엔터티, 리포지토리와 사용자 서비스를 구현하기 전에 테스트 용도로 사용할 코드
	 * 
	 */ 
	@Bean
	UserDetailsService inMemoryUserDetailsService() {
		log.info("InMemoryUserDetailsManager 생성");
		// 애플리케이션이 동작 중에 메모리에 임시 저장할 사용자 객체를 생성	
		UserDetails user1 = User.withUsername("user1") // 로그인 사용자 ID
				.password(passwordEncoder().encode("1111")) // 암호화된 로그인 사용자 비밀번호 
				.roles("USER") // 사용자 권한	
				.build();
		
		UserDetails user2 = User.withUsername("user2")
				.password(passwordEncoder().encode("2222"))
				.roles("ADMIN")
				.build();
		
		UserDetails user3 = User.withUsername("user3")
				.password(passwordEncoder().encode("3333"))
				.roles("USER", "ADMIN")
				.build();
		
		// User 타입 객체 3개를 메모리에서만 관리하는 사용자 상세정보 매니저 객체를 리턴
		return new InMemoryUserDetailsManager(user1, user2, user3);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("securityFilterChain 생성");
		
		// CSRF 기능 비활성화
		http.csrf((x) -> x.disable());
		
		// 로그인 페이지(폼) 설정을 스프링 시큐리티에서 제공하는 기본 HTML 페이지를 사용하도록 설정
		http.formLogin(Customizer.withDefaults());
		
		// 페이지 접근 권한, 인증 구성:
		http.authorizeHttpRequests(t -> 
		// 모든 요청 주소에 대해서 (권한(role)에 상관없이) 아이디/비밀번호 인증을 하는 경우:
		// t.anyRequest().authenticated());
		
		// 모든 요청 주소에 대해서 "USER" 권한을 가진 사용자의 아이디/비밀번호 인증을 하는 경우:
		// t.anyRequest().hasRole("USER")
		
		t.requestMatchers("/post/create", "/post/details", "/post/modify", "/post/delete", "/post/update", "/api/comment/**")
		.hasRole("USER")
		.anyRequest()
		.permitAll()
		
		);
		
		return http.build(); // SecurityFilterChain을 생성해서 리턴ㄴ
	}
	
}
