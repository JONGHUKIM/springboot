package com.itwill.springboot2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {
	
	private final DepartmentRepository deptRepo;
	
	public List<Department> read() {
		log.info("readList()");
		
		List<Department> departments = deptRepo.findAll();
		log.info("부서수 = {}", departments.size());
		
		return departments;
	}
	
	public Department read(Integer id) {
		log.info("read()");
		
		Department dept = deptRepo.findById(id).orElseThrow();
		
		return dept;
	}
	
}
