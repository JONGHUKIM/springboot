package com.itwill.springboot4.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.dto.CommentRegisterDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	
	@Test
	public void testCreateComment() {
		CommentRegisterDto dto = new CommentRegisterDto(53L, "댓글 입력1", "admin");
		Comment entity = commentService.create(dto);
	}
	
}
