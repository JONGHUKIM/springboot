package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.dto.DepartmentDetailsDto;
import com.itwill.springboot3.repository.DepartmentRepository;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
   
   private final DepartmentRepository deptRepo;
   private final EmployeeRepository empRepo;
   
   public Page<Department> read(int pageNo, Sort sort){
      log.info("read(pageNo={}, sort={})", pageNo, sort);
      
      Pageable pageable = PageRequest.of(pageNo, 10, sort);
      
      Page<Department> page = deptRepo.findAll(pageable);
		log.info("previous = {}", page.hasPrevious()); // 이전 페이지가 있는 지 여부
		log.info("next = {}", page.hasNext()); // 다음 페이지가 있는 지 여부 
		log.info("number = {}", page.getNumber()); // 현재 페이지(슬라이스) 번호 번호(0부터 시작)
		log.info("Total pages = {} ", page.getTotalPages()); // 전체 페이지 수 
      
      return page;
   }
   
   public DepartmentDetailsDto read(Integer id) {
      log.info("read(id={})",id);
      // 부서 아이디로 부서 엔터티 검색
      Department dept = deptRepo.findById(id).orElseThrow();
      
      // 부서 아이디로 부서에서 근무하는 직원 목록검색 
      List<Employee> employees= empRepo.findByDepartmentId(id);
      
      // 부서 상세보기 DTO 생성
      DepartmentDetailsDto dto = DepartmentDetailsDto.fromEntity(dept, employees);
      
      return dto;
   }
}
