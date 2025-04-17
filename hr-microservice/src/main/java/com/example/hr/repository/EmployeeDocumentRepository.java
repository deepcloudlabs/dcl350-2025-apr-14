package com.example.hr.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.FiatCurrency;
import com.example.hr.domain.JobStyle;

public interface EmployeeDocumentRepository extends MongoRepository<EmployeeDocument, String> {
	List<EmployeeDocument> findAllByBirthYearLessThan(int year);
	List<EmployeeDocument> findAllByJobStyle(JobStyle jobStyle);
	List<EmployeeDocument> findAllByBirthYearLessThanAndSalaryGreaterThanAndCurrency(int year,double salary,FiatCurrency currency);
	@Query("{'byear': {$lt: ?1}, 'salary': {$gt: ?2}, 'currency': ?3}")
	List<EmployeeDocument> getOldEmployeesWithHighSalary(int year,double salary,FiatCurrency currency);
    
}
