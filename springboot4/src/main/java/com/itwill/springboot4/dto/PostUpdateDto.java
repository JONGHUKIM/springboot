package com.itwill.springboot4.dto;

import lombok.Data;

// 포스트 업데이트 요청에서 전송되는 양식 데이터 파라미터들을 저장하기 위한 DTO
// 컨트롤에서 서비스 메서드를 호출할 때 아규먼트로 전달하게 될 DTO
@Data
public class PostUpdateDto {

	private Long id;
	private String title;
	private String content;

}
