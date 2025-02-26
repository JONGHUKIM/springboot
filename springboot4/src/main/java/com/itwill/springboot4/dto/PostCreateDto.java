package com.itwill.springboot4.dto;

import com.itwill.springboot4.domain.Post;

import lombok.Data;

// 클라이언트가 전송한 폼 양식 데이터를 저장하기 위한 객체.
// 필드 이름들을 요청 파라미터 이름과 같게 작성하고 기본 생성자, setter 메서드를 제공.
// 컨트롤러 메서드의 파라미터 타입으로 사용됨.
// 컨트롤러에서 서비스 메서드를 호출할 때 아규먼트로 전달함.
@Data
public class PostCreateDto {
	
	private String title;
	private String content;
	private String author;
	
	// DTO를 엔터티 타입으로 변환 리턴.
	// 서비스 메서드에서 DTO를 엔터티로 변환 후 save(insert 쿼리)할 때 사용.
	public Post toEntity() {
		
		return Post.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}

}