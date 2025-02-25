package com.itwill.springboot4.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot4.domain.Comment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentRepositoryTest {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Test
	public void testFindAll() {
		List<Comment> list = commentRepo.findAll();
		list.forEach(System.out::println);
	}

}