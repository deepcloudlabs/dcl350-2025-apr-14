package com.example.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hr.domain.FiatCurrency;
import com.example.hr.domain.JobStyle;
import com.example.hr.entity.EmployeeEntity;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, String> {
	List<EmployeeEntity> findAllByBirthYearLessThan(int year);
	List<EmployeeEntity> findAllByJobStyle(JobStyle jobStyle);
	List<EmployeeEntity> findAllByBirthYearLessThanAndSalaryGreaterThanAndCurrency(int year,double salary,FiatCurrency currency);
	@Query(nativeQuery = false,value = "select e from EmployeeEntity e where e.birthYear < :year and e.salary > :salary and e.currency = :currency")
	List<EmployeeEntity> getOldEmployeesWithHighSalary(int year,double salary,FiatCurrency currency);
    
}
