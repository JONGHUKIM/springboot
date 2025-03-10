package com.itwill.springboot4.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.domain.QPost;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostQuerydslImpl extends QuerydslRepositorySupport implements PostQuerydsl {
	
	public PostQuerydslImpl() {
		// 상위 클래스 QuerydslRepositorySupport는 기본 생성자를 갖고 있지 않기 때문에
		// 아규먼트를 갖는 생성자를 명시적으로 호출해야 함
		super(Post.class); // 아규먼트는: Entity Class
	}

	@Override
	public Post searchById(Long id) {
		log.info("searchById(id={})",id);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post); // select p from Post p
		query.where(post.id.eq(id)); // where p.id = ? 
		Post entity = query.fetchOne(); // SQL 실행 & 결과처리
		
		return entity;
	}

}
