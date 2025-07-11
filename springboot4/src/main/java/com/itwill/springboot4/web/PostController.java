package com.itwill.springboot4.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.PostCreateDto;
import com.itwill.springboot4.dto.PostListItemDto;
import com.itwill.springboot4.dto.PostSearchDto;
import com.itwill.springboot4.dto.PostUpdateDto;
import com.itwill.springboot4.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	@GetMapping("/list")
	public void list(@RequestParam(name = "p", defaultValue = "0") int pageNo, Model model) {
		log.info("list(pageNo={})", pageNo);

		// id 필드의 내림차순으로 정렬된 페이지 데이터를 가져옴.
		Page<PostListItemDto> page = postService.read(pageNo, Sort.by("id").descending());
		model.addAttribute("page", page);
		
		// pagination 프래그먼트의 링크(버튼)의 요청 주소로 사용할 문자열을 모델 속성으로 저장
		model.addAttribute("baseUrl", "/post/list");
	}

	// @PreAuthorize: 컨트롤러 메서드가 실행되기 전에 인증(로그인 여부) 확인
	// @PostAuthorize: 컨트롤러 메서드가 실행된 후에 인증 확인
//	@PreAuthorize("isAuthenticated()") // -> isAuthenticated(): 권한 상관없이 아이디/비번 확인
	@PreAuthorize("hasRole('USER')") // -> 권한(role)이 USER인 사용자 아이디/비번 인증
	@GetMapping("/create")
	public void create() {
		log.info("GET create()");
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public String create(PostCreateDto dto) {
		log.info("POST create(dto={})", dto);

		Long id = postService.create(dto);
		log.info("저장된 엔터티 id = {}", id);

		// 포스트 목록 페이지로 이동(redirect)
		return "redirect:/post/list";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping({ "/details", "/modify" })
	public void details(Long id, Model model) {
		log.info("details(id={})", id);

		// 서비스 메서드 호출, 아이디로 검색
		Post entity = postService.read(id);
		model.addAttribute("post", entity);

		// 리턴 타입이 void이기 때문에 요청 주소에 따라서 뷰의 이름이 달라짐
		// 요청 주소가 details인 경우는 details.html
		// 요청 주소가 modify인 경우는 mnodify.html
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/delete")
	public String delete(Long id) {
		log.info("delete(id={})", id);

		postService.delete(id);

		return "redirect:/post/list";

	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/update")
	public String update(PostUpdateDto dto) {
		log.info("update(dto={})", dto);

		postService.update(dto);
		log.info("업데이트된 엔터티 id = {}", dto.getId());

		return "redirect:/post/details?id=" + dto.getId();

	}

	@GetMapping("/search")
	public String search(PostSearchDto dto, Model model) {
		log.info("search(dto={})", dto);
		
		Page<PostListItemDto> page = postService.search(dto, Sort.by("id").descending());
		model.addAttribute("page", page);
		
		// pagination 프래그먼트의 링크(버튼)의 요청 주소로 사용할 문자열을 모델 속성으로 저장
		model.addAttribute("baseUrl", "/post/search");
		
		return "post/list";
	}

}