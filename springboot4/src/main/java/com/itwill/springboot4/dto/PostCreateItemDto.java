package com.itwill.springboot4.dto;

import java.time.LocalDateTime;

import com.itwill.springboot4.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostCreateItemDto {
	
	private Long id;
	private String title;
	private String content;
	private String author;
	
	public static PostCreateItemDto fromEntity(Post entity) {
		
		return PostCreateItemDto.builder().id(entity.getId()).title(entity.getTitle()).content(entity.getContent()).author(entity.getAuthor()).build();
		
	}
	
	public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
	
}
