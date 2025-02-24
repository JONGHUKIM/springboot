package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {
	
	private final EmployeeRepository empRepo;
	
	public List<Employee> read() {
		log.info("read()");
		
		List<Employee> list = empRepo.findAll();
		log.info("list size = {}", list.size());
		
		return list;
	}
	
	public Employee read(Integer id) {
		log.info("read(id={})", id);
		
		Employee emp = empRepo.findById(id).orElseThrow();
		
		return emp;
	}

}