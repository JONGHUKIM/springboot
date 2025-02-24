package com.itwill.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot3.domain.Employee;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	//@Test
	public void testDependencyInjection() {
		assertThat(empRepo).isNotNull();
		log.info("empRepo = {}",empRepo);
	}
	
	//@Test
	public void testCount() {
		// select count(*) from employees
		long count = empRepo.count();
		assertThat(count).isEqualTo(107);
	}
	
	//@Test
	public void testFindAll() {
		List<Employee> list = empRepo.findAll();
		assertThat(list.size()).isEqualTo(107);
		log.info("index 0 : {} ", list.get(0));
	}
	
	//@Test
	@Transactional
	public void testFindById() {
		// Optional은 개발자에게 해당 키 값이 없으면 어떻게 처리할건지 남겨둠
		Employee emp = empRepo.findById(101).orElseThrow();
		assertThat(emp.getFirstName()).isEqualTo("Neena");
		
		log.info("First name = {}", emp);
		log.info("emp.job = {}", emp.getJob());
		log.info("emp.manager = {}", emp.getManager());
		log.info("emp.department = {}", emp.getDepartment());
		log.info("emp.department.manager = {}", emp.getDepartment().getManager());
		log.info("emp.department.location = {}", emp.getDepartment().getLocation());
		log.info("country = {} ", emp.getDepartment().getLocation().getCountry());
		log.info("region = {}", emp.getDepartment().getLocation().getCountry().getRegion());
	}
	
	@Test
	@Transactional
	public void testJpaQueryMethods() {
		List<Employee> list;
//		list = empRepo.findByDepartmentId(100);
//		list = empRepo.findByFirstName("David");
//		list = empRepo.findByFirstNameIgnoreCase("david");
//		list = empRepo.findByFirstNameContainingIgnoreCase("da");
//		list = empRepo.findByFirstNameLike("%da%");
		
		// 이름(first_name)에 포함된 단어로 검색, 단어의 대/소문자 구분 없이 검색.
//		list = empRepo.findByFirstNameLikeIgnoreCase("%da%");
		// 이름(first_name)에 포함된 단어로 검색, 단어 대/소문자 구분 없이 검색, 정렬 순서는 이름 내림차순.
//		list = empRepo.findByFirstNameLikeIgnoreCaseOrderByFirstNameDesc("%da%");
		// 대소문자 구분없이 성(last_ame) 또는 이름(first_name)에 문자열이 포함된 직원 검색.
//		list = empRepo.findByLastNameOrFirstNameLikeIgnoreCase("%g%", "%d%");
		// 급여가 어떤 값을 초과하는 직원들 검색(where salary > ?).
		list = empRepo.findBySalaryGreaterThan((double) 23000);
		// 급여가 어떤 값 미만인 직원들 검색(where salary < ?).
		// 급여가 어떤 범위 안에 있는 직원들 검색(where salary between ? and ?).
		// 입사날짜가 특정 날짜 이전인 직원들 검색(where hire_date < ?).
		// 입사날짜가 특정 날짜 이후인 직원들 검색(where hire_date > ?).
		// 입사날짜가 날짜 범위 안에 있는 직원들 검색(where hire_date between ? and ?).
		// 부서 이름으로 직원 검색.
		// 근무 도시 이름으로 직원 검색.
		// 근무 국가 아이디로 직원 검색.

		
		list.forEach(System.out::println);
		
	}

}
