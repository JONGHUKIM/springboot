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
import com.itwill.springboot4.dto.PostSearchDto;
import com.itwill.springboot4.dto.PostUpdateDto;
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

	@Transactional
	public void delete(Long id) {
		log.debug("delete(id={})", id);

		postRepo.deleteById(id);
	}

	@Transactional
	public void update(PostUpdateDto dto) {
		log.debug("update(dto={})", dto);

		// dto에 저장된 id로 엔터티를 검색
		Post entity = postRepo.findById(dto.getId()).orElseThrow();

		// 검색한 엔터티 객체의 필드를 업데이트
		entity.update(dto.getTitle(), dto.getContent());

		// @Transactional 애너테이션이 사용된 경우,
		// DB에서 검색한 엔터티의 필드(들)이 변경되면, CrudeRepository<T, ID>.save(entity) 메서드가 자동으로 호출
		// update 쿼리가 자동으로 실행
		// @Transactional 애너테이션이 없는 경우에는 save() 메서드를 직접 호출해야 됨
	}

	@Transactional(readOnly = true)
	public Page<PostListItemDto> search(PostSearchDto dto, Sort sort) {
		log.info("search(dto={}, sort={})", dto, sort);
		
		Pageable pageable = PageRequest.of(dto.getP(), 10, sort);
		Page<Post> result = null;
		switch (dto.getCategory()) {
		case "t":
			result = postRepo.findByTitleContainingIgnoreCase(dto.getKeyword(), pageable);
			break;
		case "c":
			result = postRepo.findByContentContainingIgnoreCase(dto.getKeyword(), pageable);
			break;
		case "tc":
			result = postRepo.findByTitleOrContent(dto.getKeyword(), pageable);
			break;
		case "a":
			result = postRepo.findByAuthorContainingIgnoreCase(dto.getKeyword(), pageable);
			break;
		}
		log.info("검색 결과 전체 페이지 수 = {}", result.getTotalPages());
		
		return result.map(PostListItemDto::fromEntity);
	}

}