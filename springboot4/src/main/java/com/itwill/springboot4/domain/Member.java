package com.itwill.springboot4.domain;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
// -> onlyExplicitlyIncluded: @EqualsAndHashCode.Include 애너테이션이 사용된 필드만
// 사용할 것인 지 여부를 설정, 기본값은 false
@Entity
@Table(name = "members")
public class Member extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId // unique 제약 조건
	@Basic(optional = false) // not null 제약조건
	@EqualsAndHashCode.Include
	// -> equals(), hashcode() 메서드를 재정의할 때 사용되는 필드
	private String username;
	
	@Basic(optional = false) // not null 제약조건
	private String password;
	
	@Basic(optional = false) // not null 제약조건
	private String email;
	
}
