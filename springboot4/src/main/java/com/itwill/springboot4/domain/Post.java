package com.itwill.springboot4.domain;

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

@AllArgsConstructor(access = AccessLevel.PRIVATE) // private 생성자
@Builder
@NoArgsConstructor
@Getter

@ToString(callSuper = true)
//-> Post의 toString() 메서드를 작성할 때 상위 클래스의 toString()을 호출.

@EqualsAndHashCode(callSuper = true)
//-> Post의 equals() 메서드를 작성할 때 상위 클래스 BaseTimeEntity의 equals()를 사용.

@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {
	
	@Id  // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// primary key 값을 생성하는 방법 - 테이블 생성 시 "generated as identity"로 설정한 경우.
	private Long id;
	
	@Basic(optional = false)  // not null 제약조건
	private String title;
	
	@Basic(optional = false)
	private String content;
	
	@Basic(optional = false)
	private String author;
	
	// 포스트 수정에서 사용할 메서드 - 제목, 내용만 수정
	public Post update(String title, String content) {
		// title과 content는 null되면 안됨 ( 필요 시 )
		
		this.title = title;
		this.content = content;
		
		return this;
	}
	
}