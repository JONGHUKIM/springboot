package com.itwill.springboot2.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/emp")
public class EmployeeController {
	
	// 생성자와 final 필드를 사용한 의존성 주입
	private final EmployeeService empService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		// 서비스 계층의 메서드를 호출해서 뷰를 만들기 위한 데이터를 불러옴
		List<Employee> employees = empService.read();
		
		// 데이터를 뷰에 전달
		model.addAttribute("employees", employees);
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable Integer id, Model model) {
		log.info("details(id={})", id);
		
		Employee emp = empService.read(id);
		model.addAttribute("employee", emp);
		
		return "emp/details";
	}
	
	
	
}
