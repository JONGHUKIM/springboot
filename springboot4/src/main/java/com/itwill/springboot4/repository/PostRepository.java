package com.itwill.springboot4.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itwill.springboot4.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// JPA Query Methods
	// 제목에 포함된 문자열 대소문자 구분없이 검색
	Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
	// 내용에 포함된 문자열 대소문자 구분없이 검색
    Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    
    // 작성자에 포함된 문자열 대소문자 구분없이 검색
    Page<Post> findByAuthorContainingIgnoreCase(String keyword, Pageable pageable);
    
    // JPQL
    // 제목 또는 내용에 포함된 문자열 대소문자 구분없이 검색
    @Query("select p from Post p "
            + "where upper(p.title) like upper('%' || :keyword || '%') "
            + "or upper(p.content) like upper('%' || :keyword || '%') ")
    Page<Post> findByTitleOrContent(String keyword, Pageable pageable);
    
}