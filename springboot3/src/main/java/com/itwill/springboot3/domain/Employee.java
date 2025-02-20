package com.itwill.springboot3.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

	@Id // 엔터티 클래스는 반드시 @Id 필드를 가져야함 !!
	@Column(name = "EMPLOYEE_ID")
	private Integer id;
	
	// JPA/Hibernate는 camel 표기법의 엔터티 필드 이름을 snake 표기법에 컬럼 이름으로 매핑
	// 필드 firstName <==> 컬럼 FIRST_NAME
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate hireDate;
	private String jobId;
	private Double salary;
	private Double commissionPct;
	private Integer managerId;
	private Integer departmentId;
	
	
}
