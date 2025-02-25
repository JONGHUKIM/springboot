package com.itwill.springboot4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot4.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}