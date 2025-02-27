package com.itwill.springboot4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.CommentRegisterDto;
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

}
