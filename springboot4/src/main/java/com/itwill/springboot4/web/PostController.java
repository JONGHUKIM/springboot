package com.itwill.springboot4.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot4.domain.Post;
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
	public void list(@RequestParam(name = "p", defaultValue = "0" ) Integer pageNo, Model model) {
		log.info("pageNo = {}", pageNo);
		
		Page<Post> list = postService.read(pageNo, Sort.by("id"));
		model.addAttribute("page", list);
	}
	
}
