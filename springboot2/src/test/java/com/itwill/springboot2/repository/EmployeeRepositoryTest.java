package com.itwill.springboot2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

// import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot2.domain.Employee;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EmployeeRepositoryTest {
	
	@Autowired // 의존성 주입(Dependency Injection)
	private EmployeeRepository empRepo;
	
//	@Test
	public void test() {
		assertThat(empRepo).isNotNull(); // empRepo 객체가 null이 아니면 테스트 성공
		log.info("empRepo = {}", empRepo);
	}
	
	@Transactional
	//@Test
	public void testFindAll() {
		// findAll(): "select * from emp;" SQL을 실행하는 메서드 (전체 검색)
		List<Employee> list = empRepo.findAll(); 
		assertThat(list.size()).isEqualTo(14);
		
		list.forEach((emp) -> {
			System.out.println(emp);
			System.out.println("dept = " + emp.getDepartment());
			System.out.println("mgr = " + emp.getManager());
		});
	}
	
	@Test
	@Transactional
	public void testFindById() {
		// findById(): "select * from emp where id(empno) = ?" PK로 검색하는 메서드 (조건 검색) 
		// Optional<T>.orElseThrow(); 데이터가 있으면 T 타입의 객체를 리턴,
		// 데이터가 없으면 예외(Exception)를 발생시킴
		Employee emp = empRepo.findById(7788).orElseThrow();
		assertThat(emp.getEname()).isEqualTo("SCOTT");
		log.info("emp = {}", emp);
		log.info("dept = {}", emp.getDepartment());
		log.info("mgr = {}", emp.getManager());
		
		// Optional<T>.orElse(T other): 데이터가 있으면 T 타입의 객체를 리턴,
		// 데이터가 없으면 아규먼트로 전달된 other 객체를 리턴
		Employee emp2 = empRepo.findById(1000).orElse(null);
		assertThat(emp2).isNull();
		log.info("emp2 = {}", emp2);
		
		// Optional<T>.orElseGet(Supplier fn): 데이터가 있으면 T 타입의 객체를 리턴 
		// 데이터가 없으면 람다 표현식 fn에서 리턴하는 객체를 리턴
		Employee emp3 = empRepo.findById(1000).orElseGet(() -> null);
		assertThat(emp3).isNull();
		log.info("emp3 = {}", emp3);
	}
	

}
