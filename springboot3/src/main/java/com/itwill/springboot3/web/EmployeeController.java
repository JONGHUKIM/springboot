package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/emp")
public class EmployeeController {
	
	private final EmployeeService empService;

	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		List<Employee> list = empService.read();
		model.addAttribute("employees", list);
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable Integer id, Model model) {
		log.info("details(id = {})", id);
		
		Employee emp = empService.read(id);
		model.addAttribute("employee", emp);
		
		return "emp/details";
	}
	
}