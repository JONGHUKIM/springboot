package com.itwill.springboot4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot4.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// @EntityGraph: static 선언된 LAZY fetch 방식을 실행 시점에(runtime) EAGER 방식으로 변환
	// @EntityGraph 애너테이션을 사용하지 않았을 때는 
	// (1) select ... from members ...
	// (2) select ... from member_roles ...
	// 순서로 필요할 때 roles 정보를 가져옴 (lazy fetch)
	// @EntityGraph 애너테이션을 사용하면 
	// select ... from members m left join member_roles r on ....
	// 위와 같이 한번에 모든 정보를 가져옴(eager fetch) 
	@EntityGraph(attributePaths = "roles")
	Optional<Member> findByUsername(String username);
}
