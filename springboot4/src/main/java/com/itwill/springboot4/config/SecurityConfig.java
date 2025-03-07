package com.itwill.springboot4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// -> 스프링 컨테이너에서 생성하고 관리하는 설정 컴포넌트
// -> 스프링 컨테이너에서 필요한 곳에 의존성 주입해 줄 수 있음 

@EnableMethodSecurity
// -> 각각의 컨트롤러 메서드에서 인증(로그인), 권한 설정을 하기 위해서 
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
	/*
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
	*/
	
	/*
	 * SecurityFilterChain: 스프링 시큐리티 필터 체인 객체(bean)
	 * - 로그인/로그아웃, 인증 필터에서 필요한 설정들을 구성
	 * - 로그인 페이지(뷰), 로그아웃 페이지 설정
	 * - 페이지 접근 권한(ADMIN, USER, ...) 설정
	 * - 인증 설정(로그인 없이 접근 가능한 페이지 vs 로그인해야만 접근할 수 있는 페이지)
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("securityFilterChain 생성");
		
		// CSRF(Cross Site Request Forgery) 기능 비활성화: 
		// CSRF 기능을 활성화한 경우에는 서버로 요청을 보낼 때 csrf 토큰을 항상 같이 보내야 함
		// Ajax POST/PUT/DELETE 요청에서 csrf 토큰을 서버로 전송해야하는데, 
		// Ajax 요청에서 csrf 토큰을 전달하지 않으면 HTTP 403 에러가 발생함 
		http.csrf((x) -> x.disable());
		
		// 로그인 페이지(폼) 설정을 스프링 시큐리티에서 제공하는 기본 HTML 페이지를 사용하도록 설정
//		http.formLogin(Customizer.withDefaults());
		
		// 커스텀 로그인 페이지
		http.formLogin((login) -> login.loginPage("/member/signin"));
		
		
		/*
		 * 페이지 접근 권한, 인증 구성:
		 * 아래의 방법 1 또는 방법2 중에서 한가지를 선택
		 * 1. Http.authorizeHttpRequests(Customizer customizer) 메서드에서 설정
		 * 	  - Customizer 람다 표현식을 구현
		 * 	  - 장점: 한 곳에서 모든 설정을 구성할 수 있음
		 * 	  - 단점: 새로운 요청 경로가 생길 때마다 설정 구성 코드를 수정해야 됨
		 * 2. 컨트롤러 메서드에서 애너테이션으로 설정
		 * 	 (1) SecurityConfig 클래스(빈)는 @EnableMethodSecurity 애너테이션을 설정
		 *   (2) 각각의 컨트롤러 메서드에서 @PreAuthorize 또는 @PostAuthorize 애너테이션을 설정
		 *   - 장점: 새로운 요청 경로가 생겨도(컨트롤러가 추가되도) Config 클래스는 변경이 불필요
		 *   - 단점: 모든 설정을 한 곳에서 관리할 수 없음
		 */
		/*
		http.authorizeHttpRequests(t -> 
		// 모든 요청 주소에 대해서 (권한(role)에 상관없이) 아이디/비밀번호 인증을 하는 경우:
		// t.anyRequest().authenticated());
		
		// 모든 요청 주소에 대해서 "USER" 권한을 가진 사용자의 아이디/비밀번호 인증을 하는 경우:
		// t.anyRequest().hasRole("USER")
		
		// 로그인이 필요한 페이지와 로그인이 필요하지 않은 페이지 설정
		t.requestMatchers("/post/create", "/post/details", "/post/modify", "/post/delete", "/post/update", "/api/comment/**")
		.hasRole("USER")
		.anyRequest()
		.permitAll()
		
		);
		*/
		return http.build(); // SecurityFilterChain을 생성해서 리턴ㄴ
	}
	
}
