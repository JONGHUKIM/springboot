package com.itwill.springboot4.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.springboot4.domain.Member;
import com.itwill.springboot4.domain.MemberRole;

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
	
//	@Test
	public void testMemberRepository() {
		assertThat(memberRepo).isNotNull();
		log.info("memberRepo = {}", memberRepo);
	}
	
	@Test
	public void testMemberEquals() {
		Member m1 = Member.builder()
				.id(1L)
				.username("user1")
				.password("1111")
				.build();
		m1.addRole(MemberRole.USER);
		log.info("m1 = {}, roles = {}", m1, m1.getRoles());
		
		Member m2 = Member.builder().id(2L).username("user1").password("1234").build();
		m2.addRole(MemberRole.ADMIN);
		log.info("m2 = {}, roles = {}",m2 , m2.getRoles());
		
		assertThat(m1).isEqualTo(m2); // m1.equals(m2)의 리턴값이 true임을 주장		
		// equals(), hashCode() 메서드를 재정의할 때 username 필드만 사용
	}
	
}
