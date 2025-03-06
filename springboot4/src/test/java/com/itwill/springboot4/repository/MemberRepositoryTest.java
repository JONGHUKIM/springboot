package com.itwill.springboot4.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
//	@Test
	public void testPasswordEncoder() {
		assertThat(encoder).isNotNull();
		log.info("encoder = {}",encoder);
	}
	
	@Test
	public void testMemberRepository() {
		assertThat(memberRepo).isNotNull();
		log.info("memberRepo = {}", memberRepo);
	}
	
}
