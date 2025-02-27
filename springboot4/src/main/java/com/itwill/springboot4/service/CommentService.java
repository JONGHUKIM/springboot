package com.itwill.springboot4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.CommentRegisterDto;
import com.itwill.springboot4.dto.CommentUpdateDto;
import com.itwill.springboot4.repository.CommentRepository;
import com.itwill.springboot4.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepo;
	private final PostRepository postRepo;

	// 댓글 등록 서비스
	@Transactional
	public Comment create(CommentRegisterDto dto) {
		log.info("create(dto={})", dto);
		
		// postId로 댓글이 등록될 포스트 엔터티를 검색
		Post post = postRepo.findById(dto.getPostId()).orElseThrow();
		log.info("post={}",post);
		
		// Comment entity 객체 생성
		Comment entity = Comment.builder().post(post).text(dto.getText()).writer(dto.getWriter()).build();
		log.info("insert 전 댓글 = {}", entity);
		
		// save() 메서드 호출 -> db에 insert
		commentRepo.save(entity);
		log.info("insert 후 댓글 = {}", entity);
		
		return entity;
	}
	
	// 댓글 아이디(PK)로 댓글 1개를 검색하는 서비스
	@Transactional(readOnly = true)
	public Comment read(Long id) {
		log.info("read(id={})", id);
		
		Comment entity = commentRepo.findById(id).orElseGet(() -> null);
		log.info("댓글 = {}", entity);
		
		return entity;
	}
	
	// 댓글 목록 가져오기 서비스
	@Transactional(readOnly = true)
	public Page<Comment> read(Long postId, int pageNo, Sort sort) {
		log.info("read(postId={}, pageNo={}, sort={})", postId, pageNo, sort);
		
		Pageable pageable = PageRequest.of(pageNo, 5, sort);
		
		Post post = postRepo.findById(postId).orElseThrow();
		Page<Comment> page = commentRepo.findByPost(post, pageable);
//		Page<Comment> page = commentRepo.findByPostId(postId, pageable);
		
		return page;
	}
	
	// 댓글 삭제 서비스
	@Transactional
	public void delete(Long commentId) {
		log.info("delete(commentId = {})", commentId);
		
		commentRepo.deleteById(commentId);
	}
	
	// 댓글 업데이트 서비스
	@Transactional // 엔터티의 필드가 변경되면 save() 메서드가 실행되도록 하기 위해
	public void update(CommentUpdateDto dto) {
		log.info("update(dto = {})", dto);
		
		// 아이디로 댓글 엔터티를 검색
		Comment entity = commentRepo.findById(dto.getId()).orElseThrow();
		
		// 검색된 엔터티의 필드를 업데이트
		entity.update(dto.getText());
	}

}
