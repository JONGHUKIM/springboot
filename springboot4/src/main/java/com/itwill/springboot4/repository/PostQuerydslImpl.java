package com.itwill.springboot4.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot4.domain.Post;

public class PostQuerydslImpl extends QuerydslRepositorySupport {
	
	public PostQuerydslImpl() {
		// 상위 클래스 QuerydslRepositorySupport는 기본 생성자를 갖고 있지 않기 때문에
		// 아규먼트를 갖는 생성자를 명시적으로 호출해야 함
		super(Post.class); // 아규먼트는: Entity Class
	}

}
