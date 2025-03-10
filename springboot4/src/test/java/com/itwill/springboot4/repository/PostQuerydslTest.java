package com.itwill.springboot4.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.PostSearchDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {

	@Autowired
	private PostRepository postRepo;
	
	//@Test
	public void testSearchByid() {
		Post entity = postRepo.searchById(62L);
		log.info("entity={}",entity);
	}
	
	@Test
	public void testSearch() {
		List<Post> result = null;
//		result = postRepo.searchBykeyword("dum");
		
		PostSearchDto dto = new PostSearchDto();
		dto.setCategory("a");
		dto.setKeyword("use");
		result = postRepo.searchByCategoryAndKeyword(dto);
		
		result.forEach(System.out::println);
		
	}
	
	//@Test
	public void testSearchModifiedTime() {
		List<Post> result = null;
		
		LocalDateTime start = LocalDateTime.of(2025, 2, 20, 9, 0);
		LocalDateTime end = LocalDateTime.of(2025, 3, 10, 12, 0);
		
		result = postRepo.searchByModifiedTime(start, end);
		result.forEach(System.out::println);		
		
	}
	
}
