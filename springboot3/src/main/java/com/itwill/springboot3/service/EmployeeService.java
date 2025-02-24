package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	// paging과 sorting이 된 객체를 리턴하는 메서드 
	// pageNo: 페이지 번호, sort: 정렬 조건(컬럼,방식)
	public Page<Employee> read(int pageNo, Sort sort) {
		
		log.info("read(pageNo={}, sort={})", pageNo, sort);
		
		// Pagealbe 객체 생성
		Pageable pageable = PageRequest.of(pageNo, 10, sort);
		
		// JpaRepository<T, ID>.findAll(Pageable pageable) 메서드 호출
		Page<Employee> page = empRepo.findAll(pageable);
		log.info("previous = {}", page.hasPrevious()); // 이전 페이지가 있는 지 여부
		log.info("next = {}", page.hasNext()); // 다음 페이지가 있는 지 여부 
		log.info("number = {}", page.getNumber()); // 현재 페이지(슬라이스) 번호 번호(0부터 시작)
		log.info("Total pages = {} ", page.getTotalPages()); // 전체 페이지 수 
		
		return page;
	}
	
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