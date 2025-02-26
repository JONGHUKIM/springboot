package com.itwill.springboot4.dto;

import lombok.Data;

// 검색(/post/search) 요청에서 전송되는 요청 파라미터들을 저장하기 위한 DTO
// 컨트롤러 메서드에서 서비스 메서드를 호출할 때 아규먼트로 전달할 DTO

@Data
public class PostSearchDto {
	private String category;  // 검색 카테고리
	private String keyword;  // 검색어
	private int p;  // 검색 결과 페이지 번호
}