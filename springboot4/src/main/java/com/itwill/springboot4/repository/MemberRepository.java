package com.itwill.springboot4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot4.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
