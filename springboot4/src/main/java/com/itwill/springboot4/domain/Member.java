package com.itwill.springboot4.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
	
	@Builder.Default // Builder 패턴에서도 null이 아닌 HashSet<T> 타입 객체로 초기화될 수 있도록
	@ToString.Exclude // ToString() 메서드에서 제외
	@ElementCollection(fetch = FetchType.LAZY)
	// -> 연관 테이블 Member_roles를 엔터티 클래스 없이 사용
	// -> 데이터를 가져오는 방식(fetch 방식)은 LAZY
	@Enumerated(EnumType.STRING) // 테이블에 저장될 때 상수(enum)의 이름(문자열)로 저장
	private Set<MemberRole> roles = new HashSet<>(); // 기본 생성자에서 null이 아닌 값으로 초기화될 수 있도록
	
	public Member addRole(MemberRole role) {
		roles.add(role); // HashSet<MemberRole> roles에 원소를 추가
		
		return this;
	}
	
	public Member removeRole(MemberRole role) {
		roles.remove(role); // HashSet<MemberRole> roles에 원소를 삭제
		return this;
	}
	
	public Member clearRoles() {
		roles.clear(); //HashSet<MemberRole> roles의 모든 원소를 삭제 
		return this;
	}
	
}
