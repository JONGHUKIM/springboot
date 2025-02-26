package com.itwill.springboot4.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.dto.PostCreateDto;
import com.itwill.springboot4.dto.PostListItemDto;
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
	}
	
	@GetMapping("/create")
	public void create() {
		log.info("GET create()");
	}
	
	@PostMapping("/create")
	public String create(PostCreateDto dto) {
		log.info("POST create(dto={})", dto);
		
		Long id = postService.create(dto);
		log.info("저장된 엔터티 id = {}", id);
		
		// 포스트 목록 페이지로 이동(redirect)
		return "redirect:/post/list";
	}
	
	@GetMapping({"/details", "/modify"})
	public void details(Long id, Model model) {
		log.info("details(id={})", id);
		
		// 서비스 메서드 호출, 아이디로 검색
		Post entity = postService.read(id);
		model.addAttribute("post", entity);
		
		// 리턴 타입이 void이기 때문에 요청 주소에 따라서 뷰의 이름이 달라짐
		// 요청 주소가 details인 경우는 details.html
		// 요청 주소가 modify인 경우는 mnodify.html
	}
	
	@GetMapping("/delete")
	public String delete(Long id) {
		log.info("delete(id={})", id);
		
		postService.delete(id);
		
		return "redirect:/post/list";
		
	}
	
	

}