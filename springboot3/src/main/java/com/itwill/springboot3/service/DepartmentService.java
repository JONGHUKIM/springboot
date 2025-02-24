package com.itwill.springboot3.service;

import java.util.List;

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
   
   public List<Department> read(){
      log.info("read()");
      List<Department> list = deptRepo.findAll();
      
      return list;
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
