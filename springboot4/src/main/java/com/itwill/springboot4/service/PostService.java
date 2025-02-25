package com.itwill.springboot4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepo;

	public Page<Post> read(int pageNo, Sort sort) {
		log.info("read(pageNo={}, sort={})", pageNo, sort);
		
		Pageable pageable = PageRequest.of(pageNo, 10, sort);
		
		Page<Post> page = postRepo.findAll(pageable);
		log.info("previous = {}", page.hasPrevious()); // 이전 페이지가 있는 지 여부
		log.info("next = {}", page.hasNext()); // 다음 페이지가 있는 지 여부 
		log.info("number = {}", page.getNumber()); // 현재 페이지(슬라이스) 번호 번호(0부터 시작)
		log.info("Total pages = {} ", page.getTotalPages()); // 전체 페이지 수 
		
		return page;
	}
	
}
