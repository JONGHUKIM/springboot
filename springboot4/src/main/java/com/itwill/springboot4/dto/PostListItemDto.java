package com.itwill.springboot4.dto;

import java.time.LocalDateTime;

import com.itwill.springboot4.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// PostService 전체 검색 메서드에서 PostController로 전달(리턴)할 DTO
// 엔터티 필드들 중에서 포스트 목록 페이지에서 보여줄 데이터들만 선별.
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostListItemDto {
	
	private Long id;
	private String title;
	private String author;
	private LocalDateTime modifiedTime;
	
	// Post 엔터티를 PostListItemDto 타입 객체로 변환 리턴
	public static PostListItemDto fromEntity(Post entity) {
		
		return PostListItemDto.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.author(entity.getAuthor())
				.modifiedTime(entity.getModifiedTime())
				.build();
	}

}