package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.dto.response.EmployeeResponse;

@Configuration
public class ModelMapperConfig {

	private static final Converter<Employee,EmployeeResponse> Employee2EmployeeResponseConverter 
	= context -> {
		var employee = context.getSource();
		return new EmployeeResponse(
				employee.getIdentity().getValue(),
				employee.getFullName().firstName(),
				employee.getFullName().lastName(),
				employee.getIban().getValue(),
				employee.getSalary().value(),
				employee.getSalary().currency(),
				employee.getDepartments().stream().map(Department::name).toList(),
				employee.getJobStyle().name(),
				employee.getBirthYear().value()
		);
	} ;

	@Bean 
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(Employee2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		return modelMapper;
	}
}
