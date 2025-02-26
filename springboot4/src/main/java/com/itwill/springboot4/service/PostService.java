package com.itwill.springboot4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.PostCreateDto;
import com.itwill.springboot4.dto.PostListItemDto;
import com.itwill.springboot4.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepo;
	
	@Transactional(readOnly = true)
	public Page<PostListItemDto> read(int pageNo, Sort sort) {
		log.info("read(pageNo={}, sort={})", pageNo, sort);
		
		// 한 페이지에 10개씩 보여줌.
		Pageable pageable = PageRequest.of(pageNo, 10, sort);
		Page<Post> list = postRepo.findAll(pageable);
		
		// Page<Post> 타입 객체를 Page<PostListItemDto> 타입 객체로 변환.
		Page<PostListItemDto> page = list.map(PostListItemDto::fromEntity);
		
		return page;
	}
	
	@Transactional
	public Long create(PostCreateDto dto) {
		log.info("create(dto={})", dto);
		
		// DTO를 엔터티로 변환하고 저장(insert query 실행).
		Post entity = postRepo.save(dto.toEntity());
		log.info("저장된 엔터티 = {}", entity);
		
		// 저장된 엔터티의 PK(id)를 리턴.
		return entity.getId();
	}
	
	@Transactional(readOnly = true)
	public Post read(Long id) {
		log.debug("read(id={})", id);
		
		return postRepo.findById(id).orElseThrow();
		
	}

}