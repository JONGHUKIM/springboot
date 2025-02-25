package com.itwill.springboot4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
 * JPA auditing:
 * insert/update 쿼리를 실행할 때 시간 정보(created, modified)를 자동으로 저장하는 기능.
 * 엔터티 생성/수정 시간을 자동으로 기록하기 위해선 JPA auditing 기능을 활성화시켜야 함.
 * (1) 스프링 부트 애플리케이션 메인 클래스(Springboot4Application)에서
 * @EnableJpaAuditing 애너테이션을 설정.
 * (2) 날짜(LocalDate)/시간(LocalDateTime) 필드를 갖는 엔터티 클래스(BaseTimeEntity)에서
 * @EntityListeners(AuditingEntityListener.class) 애너테이션을 설정.
 * (3) 날짜/시간 필드(createdTime/modifiedTime)에
 * 생성 시간은 @CreatedDate, 최종 수정 시간은 @LastModifiedDate 애너테이션을 설정.
 */

@EnableJpaAuditing  // JPA auditing 기능 활성화.
@SpringBootApplication
public class Springboot4Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot4Application.class, args);
	}

}