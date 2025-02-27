package com.itwill.springboot4.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.dto.CommentRegisterDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	
	//@Test
	public void testCreateComment() {
		CommentRegisterDto dto = new CommentRegisterDto(53L, "댓글 입력1", "admin");
		Comment entity = commentService.create(dto);
	}
	
	//@Test
	public void testReadById() {
		// 댓글 아이디가 DB에 있는 경우 
		Comment comment1 = commentService.read(21L);
		assertThat(comment1).isNotNull();
		log.info("comment1 = {}", comment1);
		
		// 댓글 아이디가 DB에 없는 경우
		Comment comment2 = commentService.read(10L);
		assertThat(comment2).isNull();
		log.info("comment2 = {}",comment2);
	}
	
	//@Test
	public void makeDummyComments() {
		for (int i = 1; i <= 10; i++) {
			CommentRegisterDto dto = new CommentRegisterDto(53L, "댓글 놀이 #" + i, "admin");
			Comment entity = commentService.create(dto);
			assertThat(entity).isNotNull();
		}
	}
	
	@Test
	public void testReadByPostId() {
		Page<Comment> page = commentService.read(53L, 1, Sort.by("id").descending());
		page.forEach(System.out::println);
	}
	
}
