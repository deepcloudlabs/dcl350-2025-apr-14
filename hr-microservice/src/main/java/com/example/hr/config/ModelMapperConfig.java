package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeQLResponse;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.entity.EmployeeEntity;

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
	
	private static final Converter<HireEmployeeRequest,Employee> HireEmployeeRequest2EmployeeConverter 
	= context -> {
		var request = context.getSource();
		return new Employee.Builder()
		        .identity(request.identity())
		        .fullName(request.firstName(), request.lastName())
		        .salary(request.salary(),request.currency())
		        .iban(request.iban())
		        .birthYear(request.birthYear())
		        .departments(request.departments())
		        .jobStyle(request.jobStyle())
		        .photo(request.photo())
		        .build();
	};
			
	private static final Converter<Employee,FireEmployeeResponse> Employee2FireEmployeeResponseConverter 
	= context -> {
		var employee = context.getSource();
		return new FireEmployeeResponse(
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

	private static final Converter<EmployeeEntity,Employee> EmployeeEntity2EmployeeConverter 
	= context -> {
		var entity = context.getSource();
		return new Employee.Builder()
		        .identity(entity.getIdentity())
		        .fullName(entity.getFirstName(), entity.getLastName())
		        .salary(entity.getSalary(),entity.getCurrency())
		        .iban(entity.getIban())
		        .birthYear(entity.getBirthYear())
		        .departments(entity.getDepartments())
		        .jobStyle(entity.getJobStyle())
		        .photo(entity.getPhoto())
		        .build();
	};

	private static final Converter<Employee,EmployeeEntity> Employee2EmployeeEntityConverter 
	= context -> {
		var employee = context.getSource();
		var entity = new EmployeeEntity();
		entity.setIdentity(employee.getIdentity().getValue());
		entity.setFirstName(employee.getFullName().firstName());
		entity.setLastName(employee.getFullName().lastName());
		entity.setIban(employee.getIban().getValue());
		entity.setSalary(employee.getSalary().value());
		entity.setCurrency(employee.getSalary().currency());
		entity.setDepartments(employee.getDepartments().stream().map(Department::name).toList());
		entity.setJobStyle(employee.getJobStyle().name());
		entity.setBirthYear(employee.getBirthYear().value());
		entity.setPhoto(employee.getPhoto().values());
		return entity;
	} ;
	
	private static final Converter<Employee,EmployeeQLResponse> Employee2EmployeeQLResponseConverter 
	= context -> {
		var employee = context.getSource();
		return new EmployeeQLResponse(
				employee.getIdentity().getValue(),
				employee.getFullName().firstName(),
				employee.getFullName().lastName(),
				employee.getIban().getValue(),
				employee.getSalary().value(),
				employee.getSalary().currency(),
				employee.getDepartments().stream().map(Department::name).toList(),
				employee.getJobStyle().name(),
				employee.getBirthYear().value(),
				employee.getPhoto().toString()
		);
	} ;	
	
	@Bean 
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(Employee2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		modelMapper.addConverter(HireEmployeeRequest2EmployeeConverter, HireEmployeeRequest.class, Employee.class);
		modelMapper.addConverter(Employee2FireEmployeeResponseConverter,Employee.class,FireEmployeeResponse.class);
		modelMapper.addConverter(Employee2EmployeeResponseConverter,Employee.class,EmployeeResponse.class);
		modelMapper.addConverter(Employee2EmployeeQLResponseConverter, Employee.class, EmployeeQLResponse.class);
		modelMapper.addConverter(Employee2EmployeeEntityConverter, Employee.class, EmployeeEntity.class);
		modelMapper.addConverter(EmployeeEntity2EmployeeConverter, EmployeeEntity.class, Employee.class);
		return modelMapper;
	}
}
