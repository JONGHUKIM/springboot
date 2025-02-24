package com.itwill.springboot3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.springboot3.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	// JPA Query Method:
	// JPA에서 미리 약속된 키워드들과 엔터티의 필드 이름들을 사용해서
	// 메서드 이름을 (camel 표기법으로) 작성.
	
	// 부서 번호가 일치하는 직원들 검색:
	// select * from employees where department_id = ?
	List<Employee> findByDepartmentId(Integer id);
	
	// 이름(first_name)이 일치하는 직원(들) 검색:
	// select * from employees where first_name = ?
	List<Employee> findByFirstName(String firstName);
	
	// 이름(first_name)이 일치하는 직원(들), 대/소문자는 구분없이 검색:
	// select * from employees where upper(first_name) = upper(?)
	List<Employee> findByFirstNameIgnoreCase(String firstName);
	
	// 이름(first_name)에 포함된 문자열로 검색:
	// select * from employees where first_name like '%' || ? || '%'
	List<Employee> findByFirstNameContaining(String keyword);
	
	// 비교:
	// Containing을 사용하면 아규먼트의 앞/뒤에 "%"를 붙이고 like 검색을 수행.
	// Like를 사용할 때는 아규먼트가 "%"를 포함하고 있어야 함.
	List<Employee> findByFirstNameLike(String keyword);
	
	// 이름(first_name)에 포함된 단어로 검색, 단어의 대/소문자 구분 없이 검색.
	List<Employee> findByFirstNameContainingIgnoreCase(String keyword);
	
	// 이름(first_name)에 포함된 단어로 검색, 단어 대/소문자 구분 없이 검색, 정렬 순서는 이름 내림차순.
	List<Employee> findByFirstNameContainingIgnoreCaseOrderByFirstNameDesc(String keyword);
	
	// 대소문자 구분없이 성(last_ame) 또는 이름(first_name)에 문자열이 포함된 직원 검색.
	List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
	
	// 급여가 어떤 값을 초과하는 직원들 검색(where salary > ?).
	List<Employee> findBySalaryGreaterThan(double salary);
	
	// 급여가 어떤 값 미만인 직원들 검색(where salary < ?).
	List<Employee> findBySalaryLessThan(double salary);
	
	// 급여가 어떤 범위 안에 있는 직원들 검색(where salary between ? and ?).
	List<Employee> findBySalaryBetween(double min, double max);
	
	// 입사날짜가 특정 날짜 이전인 직원들 검색(where hire_date < ?).
	List<Employee> findByHireDateLessThan(LocalDate date);
	
	// 입사날짜가 특정 날짜 이후인 직원들 검색(where hire_date > ?).
	List<Employee> findByHireDateGreaterThan(LocalDate date);
	
	// 입사날짜가 날짜 범위 안에 있는 직원들 검색(where hire_date between ? and ?).
	List<Employee> findByHireDateBetween(LocalDate start, LocalDate end);
	
	// 부서 이름으로 직원 검색.
	List<Employee> findByDepartmentDepartmentName(String deptName);
	
	// 근무 도시 이름으로 직원 검색.
	List<Employee> findByDepartmentLocationCity(String city);
	
	// 근무 국가 아이디로 직원 검색.
	List<Employee> findByDepartmentLocationCountryId(String city);
	
	
	// JPQL(Java Persistence Query Language)
	// JPA에서 사용하는 "객체 지향(object-oriented)" 쿼리 문법.
	// 테이블 이름과 컬럼 이름을 사용해서 쿼리 문장을 작성하는 것이 아니라,
	// 엔터티 클래스 이름과 엔터티 필드 이름을 사용해서 쿼리를 작성하는 문법.
	// alias(별명)을 반드시 사용해야 함.
	// 엔터티 이름, 필드 이름들은 대소문자를 구분.
	
	@Query("select e from Employee e "
			+ "where upper(e.firstName) like upper('%' || ?1 || '%') "
			+ "or upper(e.lastName) like upper('%' || ?2 || '%')")
	List<Employee> findByName(String firstName, String lastName);
	
	@Query("select e from Employee e "
			+ "where upper(e.firstName) like upper('%' || :keyword || '%') "
			+ "or upper(e.lastName) like upper('%' || :keyword || '%')")
	List<Employee> findByName(@Param("keyword") String name);
	
	@Query("select e from Employee e "
			+ "where e.department.departmentName = :dname")
	List<Employee> findByDeptName(@Param("dname") String deptName);
	
	// 특정 도시 (이름)에 근무하는 직원들 검색
	@Query("select e from Employee e "
			+ "where e.department.location.city = :city")
	// 메서드 파라미터 이름과 쿼리의 바인딩 파라미터 이름이 같은 경우에는 @Param 생략 가능.
	List<Employee> findByCity(String city);
	
	// 특정 국가 (이름)에 근무하는 직원들 검색
	@Query("select e from Employee e "
			+ "where e.department.location.country.countryName = :country")
	List<Employee> findByCountry(String country);
	
}