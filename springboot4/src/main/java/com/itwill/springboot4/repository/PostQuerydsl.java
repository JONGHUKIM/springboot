package com.itwill.springboot4.repository;

import com.itwill.springboot4.domain.Post;

public interface PostQuerydsl {
	
	// 포스트 아이디(id)가 일치하는 엔터티 검색
	Post searchById(Long id);

}
