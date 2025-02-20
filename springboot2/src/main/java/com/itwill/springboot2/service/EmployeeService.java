package com.itwill.springboot2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

	// 생성자와 final 필드를 사용한 의존성 주입
	private final EmployeeRepository empRepo;

	public List<Employee> read() {
		log.info("read()");

		// 영속성(persistence) 계층의 메서드를 호출해서 필요한 SQL 실행
		List<Employee> employees = empRepo.findAll();
		log.info("직원수 = {}", employees.size());

		// 결과를 컨트롤러에게 리턴
		return employees;
	}

	public Employee read(Integer id) {
		log.info("read(id={})", id);

		Employee emp = empRepo.findById(id).orElseThrow();

		return emp;
	}

}
