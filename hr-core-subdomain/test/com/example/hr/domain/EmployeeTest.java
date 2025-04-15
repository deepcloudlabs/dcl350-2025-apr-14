package com.example.hr.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class EmployeeTest {

	@ParameterizedTest
	@CsvFileSource(resources = "employees.csv")
	void createEmployeeSuccessfuly(String identity,String firstName,String lastName,double salary, String currency,String iban,String departments,String jobStyle,int birthYear,String photo) {
		var employee = new Employee.Builder()
				                   .identity(identity)
				                   .fullName(firstName, lastName)
				                   .salary(salary,currency)
				                   .iban(iban)
				                   .birthYear(birthYear)
				                   .departments(departments.split(":"))
				                   .photo(photo)
				                   .build();
		assertNotNull(employee);
		assertAll(
		  () -> assertEquals(identity,employee.getIdentity().getValue()),
		  () -> assertEquals(firstName,employee.getFullName().firstName())
		);
	}

}
